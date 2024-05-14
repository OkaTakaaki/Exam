package ScoreManagement.M.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ScoreManagement.M.model.StudentModel;
import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.service.StudentService;
import io.micrometer.common.lang.NonNull;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/studenttop/") // HTTP GETリクエストを"/studenttop/"エンドポイントで処理する
	public String top(Model model, @AuthenticationPrincipal UserDetails user) { // モデルを受け取る
	    // 学生の一覧を取得してモデルに追加する
	    model.addAttribute("studentlist", this.studentService.getResStudentList(user));
	    // "studenttop"テンプレート名を返す
	    return "studenttop";
	}

	
	@PostMapping("/studenttop/") // HTTP POSTリクエストを"/studenttop/"エンドポイントで処理する
	public String handleListActions(
	        @RequestParam(name = "entYear", required = false) Integer entYear, // 入学年をリクエストから取得（オプション）
	        @RequestParam(name = "classNum", required = false) String classNum, // クラス番号をリクエストから取得（オプション）
	        @RequestParam(name = "isAttend", required = false) Boolean isAttend, // 出席状況をリクエストから取得（オプション）
	        Model model, @AuthenticationPrincipal TeacherModel teacher ) { // モデルを受け取る
	    // 検索操作の場合
	    // 学生をフィルタリングするためにサービスを呼び出し、フィルタリングされた学生リストを取得
	    List<StudentModel> students = studentService.filterStudents(entYear, classNum, isAttend, teacher.getSchoolCd());
	    // モデルにフィルタリングされた学生リストを"studentlist"属性として追加
	    model.addAttribute("studentlist", students);
	    // 検索結果のテンプレート名を返す
	    return "studenttop";
	}

	
	@GetMapping("/studentadd/")
	public ModelAndView add(StudentModel student, ModelAndView model) {
		model.addObject("student", student);
		model.setViewName("studentform");
		return model;
	}
 
	@PostMapping("/studentadd/")
	public String add(@Validated @ModelAttribute @NonNull StudentModel student, RedirectAttributes result, ModelAndView model,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		try {
			this.studentService.save(student,user);
			redirectAttributes.addFlashAttribute("exception", "");
 
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("exception", e.getMessage());
		}
		return "redirect:/studenttop/";
	}
	
	
	// 編集画面を表示する
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	    StudentModel student = studentService.getOneBook(id);
	    model.addAttribute("student", student);
	    return "studentupdate";
	}
		
	// 情報を更新する
	@PostMapping("/update/{id}")
	public String update(@ModelAttribute @Validated StudentModel student, BindingResult result, Model model) {
			
	    // バリデーションエラーの場合
	    if (result.hasErrors()) {
	        // 編集画面に遷移
	        return "studentupdate";
	    }
		
	    // 更新する
	    studentService.update(student);
		
	    // studentの一覧画面にリダイレクト
	    return "studentupdatecomplate";
	}
	
}
