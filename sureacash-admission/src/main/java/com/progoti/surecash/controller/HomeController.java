package com.progoti.surecash.controller;

import com.progoti.surecash.admission.converter.UniversityConverter;
import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	private EnquiryRepository enquiryRepository;

	@Autowired
    public HomeController(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    @GetMapping(value = { "/", "/home" })
	public String getHomePage(Model model) {
		return "application/apply";
	}

    @GetMapping(value="/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

	@GetMapping(value = "/general-enquiry")
	public String enquiryForm(Model model, @ModelAttribute("enquiry") Enquiry enquiry) {
		model.addAttribute("submitted", false);
		return "general_enquiry";
	}

	@PostMapping(value = "/submit-enquiry")
	public String submitEnquiry(Model model, @ModelAttribute("enquiry") Enquiry enquiry, HttpServletRequest servletRequest) {
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
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

    @GetMapping("/contact")
    public String contactUs(Model model, HttpServletRequest request) {
        University university = (University) request.getServletContext().getAttribute(request.getServerName());
        model.addAttribute("contact", UniversityConverter.toContactDto(university));
        return "contact";
    }

    @GetMapping("/howtopay")
    public String howToPay() {
	    return "how-to-pay";
    }
}
