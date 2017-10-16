package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.form.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	private EnquiryRepository enquiryRepository;
	private AdmissionService admissionService;
	private StudentInfoRepository studentInfoRepository;

	@Autowired
    public HomeController(EnquiryRepository enquiryRepository,
            AdmissionService admissionService,
            StudentInfoRepository studentInfoRepository) {
        this.enquiryRepository = enquiryRepository;
        this.admissionService = admissionService;
        this.studentInfoRepository = studentInfoRepository;
    }

    @GetMapping(value = { "/", "/home" })
	public String greetingForm(Model model) {
		return "application/apply";
	}

	@GetMapping(value = "/general-enquiry")
	public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
		UserDetailsImpl user = SecurityUtils.getUserDetails();
		System.out.println(user.getUser().getUserName() + "-->" + user.getUser().getUniv().getId());
		if(user.getUser().getRole().equalsIgnoreCase("ADMIN")) {
			return "redirect:/admin/dashboard";
		}
		model.addAttribute("submitted", false);
		return "general_enquiry";
	}

    @GetMapping(value = "/edit-profile")
    public String editProfile(Model model) {
        UserDetailsImpl user = SecurityUtils.getUserDetails();
        ProfileResponse profile = admissionService.getStudentProfile(studentInfoRepository
                .findOneByUserNameAndUniversity(user.getUser().getUserName(),
                        user.getUser().getUniv()));
        model.addAttribute("profile", profile);
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
