package com.progoti.surecash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.progoti.surecash.dto.form.Greeting;

@Controller
public class HomeController {

	@GetMapping(value = {"/","/home"})
	public String greetingForm(Model model) {
		
		// Add model attributes here
		//model.addAttribute("newForm", new Greeting());
		return "home";
	}
	
	@PostMapping("/processgreeting")
	public String processGreetingForm(@ModelAttribute("newForm") Greeting greeting) {
		System.out.println(greeting.getId());
		System.out.println(greeting.getStringValue());
		return "redirect:/greeting";
	}

}
