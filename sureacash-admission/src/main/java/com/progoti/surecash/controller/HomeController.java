package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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
	public String getHomePage(Model model) {
		return "application/apply";
	}

	@GetMapping(value = "/general-enquiry")
	public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
		UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
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
	public String submitEnquiry(Model model, @ModelAttribute("enquiry") Enquiry enquiry, HttpServletRequest servletRequest) {
        University university = (University) servletRequest.getAttribute("university");
        enquiry.setUniversityId(university.getId());
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
        if(userDetails != null){
            Long studentId = (long) userDetails.getUser().getStudentId().getId();
            enquiry.setStudentId(studentId);
        }
		enquiryRepository.save(enquiry);
		model.addAttribute("submitted", true);
		return "general_enquiry";
	}

}
