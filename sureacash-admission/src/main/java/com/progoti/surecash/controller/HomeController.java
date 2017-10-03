package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.dto.form.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private EnquiryRepository enquiryRepository;

	@GetMapping(value = {"/","/home"})
	public String greetingForm(Model model) {
		return "home";
	}

    @GetMapping(value = "/general-enquiry")
    public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
        model.addAttribute("submitted", false);
        return "general_enquiry";
    }

    @PostMapping(value="/submit-enquiry")
    public String submitEnquiry(Model model, @ModelAttribute("enquiry") Enquiry enquiry){
        enquiryRepository.save(enquiry);
        model.addAttribute("submitted", true);
        return "general_enquiry";
    }
	
	@PostMapping("/processgreeting")
	public String processGreetingForm(@ModelAttribute("newForm") Greeting greeting) {
		System.out.println(greeting.getId());
		System.out.println(greeting.getStringValue());
		return "redirect:/greeting";
	}

}
