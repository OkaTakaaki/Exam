package ScoreManagement.M.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/top/")
	public String schooltop(Model model) {
		return "top";
	}

}
