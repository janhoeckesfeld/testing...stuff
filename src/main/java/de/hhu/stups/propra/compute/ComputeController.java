package de.hhu.stups.propra.compute;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ComputeController {

	@GetMapping(path = "/")
	public String homePage(Model model) {
		return "start";
	}

	@PostMapping(path = "/")
	public String calculate(Model model, Summe s) {
		model.addAttribute("s", s);
		return "start";
	}

	@ExceptionHandler(Exception.class)
	public RedirectView exception() {
		return new RedirectView("http://www.onlinewahn.de/ende.htm");
	}

}
