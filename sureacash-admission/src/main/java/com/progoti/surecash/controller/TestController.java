package com.progoti.surecash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.progoti.surecash.dto.form.Greeting;

@Controller
public class TestController {

	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		model.addAttribute("newForm", new Greeting());
		return "greeting";
	}
	
	@PostMapping("/processgreeting")
	public String processGreetingForm(@ModelAttribute("newForm") Greeting greeting) {
		System.out.println(greeting.getId());
		System.out.println(greeting.getStringValue());
		return "redirect:/greeting";
	}

}
