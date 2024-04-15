package ScoreManagement.M.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ScoreManagement.M.model.SchoolModel;
import ScoreManagement.M.service.SchoolService;
import io.micrometer.common.lang.NonNull;


@Controller
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	
	@GetMapping("/schooltop/")
	public String schooltop(Model model) {
		model.addAttribute("schoollist", this.schoolService.getSchoolList());
		return "schooltop";
	}
	
	@GetMapping("/schooladd/")
	public ModelAndView add(SchoolModel school, ModelAndView model) {
		model.addObject("school", school);
		model.setViewName("schoolform");
		return model;
	}
 
	@PostMapping("/schooladd/")
	public String add(@Validated @ModelAttribute @NonNull SchoolModel school, RedirectAttributes result, ModelAndView model,
			RedirectAttributes redirectAttributes) {
		try {
			this.schoolService.save(school);
			redirectAttributes.addFlashAttribute("exception", "");
 
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("exception", e.getMessage());
		}
		return "redirect:/schooltop/";
	}
	
	@GetMapping("/schooldelete/{id}")
	public ModelAndView delete(@PathVariable(name = "id") Long id, SchoolModel schoolModel, ModelAndView model) {
		this.schoolService.delete(id);
		model.setViewName("delete");
		return model;
	}
	
		
}

