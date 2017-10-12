package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.PaymentRequestDto;
import com.progoti.surecash.dto.UnitDto;
import com.progoti.surecash.dto.form.Greeting;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@Autowired
	private EnquiryRepository enquiryRepository;
	@Autowired
	private AdmissionService admissionService;
	@Autowired
	private StudentInfoRepository studentInfoRepository;

	@GetMapping(value = { "/", "/home" })
	public String greetingForm(Model model) {
		return "home";
	}

	@GetMapping(value = "/general-enquiry")
	public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
		UserDetailsImpl user = SecurityUtils.getUserDetails();
		System.out.println(user.getUser().getUserName() + "-->" + user.getUser().getUniv().getId());
		model.addAttribute("submitted", false);
		return "general_enquiry";
	}

	@GetMapping(value = "/edit-profile")
	public String editProfile(Model model) {
	    UserDetailsImpl user = SecurityUtils.getUserDetails();
		model.addAttribute("profile", admissionService.getStudentProfile(studentInfoRepository.getOne(7)));
		return "edit_profile";
	}

	@PostMapping(value = "/submit-enquiry")
	public String submitEnquiry(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
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
