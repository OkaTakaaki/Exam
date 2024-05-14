package ScoreManagement.M.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ScoreManagement.M.model.SubjectModel;
import ScoreManagement.M.service.SubjectService;
import io.micrometer.common.lang.NonNull;

@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	@GetMapping("/subjecttop/") // HTTP GETリクエストを"/studenttop/"エンドポイントで処理する
	public String top(Model model, @AuthenticationPrincipal UserDetails user) { // モデルを受け取る
	    // 学生の一覧を取得してモデルに追加する
	    model.addAttribute("subjectlist", this.subjectService.getResSubjectList(user));
	    // "studenttop"テンプレート名を返す
	    return "subjecttop";
	}
	
	
	@GetMapping("/subjectadd/")
	public ModelAndView add(SubjectModel subject, ModelAndView model) {
		model.addObject("subject", subject);
		model.setViewName("subjectform");
		return model;
	}
 
	@PostMapping("/subjectadd/")
	public String add(@Validated @ModelAttribute @NonNull SubjectModel subject, RedirectAttributes result, ModelAndView model,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
		try {
			this.subjectService.save(subject, user);
			redirectAttributes.addFlashAttribute("exception", "");
 
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("exception", e.getMessage());
		}
		return "redirect:/subjecttop/";
	}
	
	
	// 編集画面を表示する
	@GetMapping("/subject/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	    SubjectModel subject = subjectService.getOneBook(id);
	    model.addAttribute("subject", subject);
	    return "subjectupdate";
	}
		
	// 情報を更新する
	@PostMapping("/subject/update/{id}")
	public String update(@ModelAttribute @Validated SubjectModel subject, BindingResult result, Model model) {
			
	    // バリデーションエラーの場合
	    if (result.hasErrors()) {
	        // 編集画面に遷移
	        return "subjectupdate";
	    }
		
	    // 更新する
	    subjectService.update(subject);
		
	    // subjectの一覧画面にリダイレクト
	    return "redirect:/subjecttop/";
	}
	
	@GetMapping("/subject/delete/{id}")
	public ModelAndView delete(@PathVariable(name = "id") Long id, SubjectModel subjectModel, ModelAndView model) {
		this.subjectService.delete(id);
		model.setViewName("delete");
		return model;
	}
}
