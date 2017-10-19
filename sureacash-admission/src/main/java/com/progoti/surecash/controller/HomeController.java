package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.Constants;
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

	@Autowired
    public HomeController(EnquiryRepository enquiryRepository,
            AdmissionService admissionService) {
        this.enquiryRepository = enquiryRepository;
        this.admissionService = admissionService;
    }

    @GetMapping(value = { "/", "/home" })
	public String greetingForm(Model model) {
		return "application/apply";
	}

	@GetMapping(value = "/general-enquiry")
	public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
		UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
//		System.out.println(userDetails.getUser().getUserName() + "-->" + userDetails.getUser().getUniv().getId());
		if(userDetails != null && userDetails.getUser().getRole().getRoleName() == Constants.RoleName.ADMIN) {
			return "redirect:/admin/dashboard";
		}
		model.addAttribute("submitted", false);
		return "general_enquiry";
	}

    @GetMapping(value = "/edit-profile")
    public String editProfile(Model model) {
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
        ProfileResponse profile = admissionService.getStudentProfile(userDetails.getUser().getStudentId());
        model.addAttribute("profile", profile);
        return "edit_profile";
    }

	@PostMapping(value = "/submit-enquiry")
	public String submitEnquiry(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
        // TODO: need to change in insert column (university_id, student_id)
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
