package ScoreManagement.M.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ScoreManagement.M.service.StudentService;
import io.micrometer.common.lang.NonNull;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/studenttop/")
	public String top(Model model) {
		model.addAttribute("studentlist", this.studentService.getStudentList());
		return "studenttop";
	}
	
	@PostMapping("/studenttop/")
    public String handleListActions(
            @RequestParam(name = "entYear", required = false) Integer entYear,
            @RequestParam(name = "classNum", required = false) String classNum,
            @RequestParam(name = "isAttend", required = false) Boolean isAttend,
            Model model) {
        // 検索操作の場合
        List<StudentModel> students = studentService.filterStudents(entYear, classNum, isAttend);
        System.out.println("検索結果: " + students);
        model.addAttribute("searchedStudents", students);
        return "studenttop"; // 検索結果のテンプレート名を返す
	}
	
	@GetMapping("/studentadd/")
	public ModelAndView add(StudentModel student, ModelAndView model) {
		model.addObject("student", student);
		model.setViewName("studentform");
		return model;
	}
 
	@PostMapping("/studentadd/")
	public String add(@Validated @ModelAttribute @NonNull StudentModel student, RedirectAttributes result, ModelAndView model,
			RedirectAttributes redirectAttributes) {
		try {
			this.studentService.save(student);
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
	    return "redirect:/studenttop/";
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable(name = "id") Long id, StudentModel studentModel, ModelAndView model) {
		this.studentService.delete(id);
		model.setViewName("delete");
		return model;
	}
}
