package com.progoti.surecash.controller;

import com.progoti.surecash.admission.converter.UniversityConverter;
import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.service.AdmitCardGenService;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

	private final ApplicationContext appContext;

	private final AdmitCardGenService admitCardGenService;

	private EnquiryRepository enquiryRepository;

	@Autowired
    public HomeController(EnquiryRepository enquiryRepository, ApplicationContext appContext, AdmitCardGenService admitCardGenService) {
        this.enquiryRepository = enquiryRepository;
        this.appContext = appContext;
        this.admitCardGenService = admitCardGenService;
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
		UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
		if(userDetails != null && userDetails.getUser().getRole().getRoleName() == Constants.RoleName.ADMIN) {
			return "redirect:/admin/dashboard";
		}
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

	@RequestMapping(path = "/pdf", method = RequestMethod.GET)
	public ModelAndView report() {

		JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:reports/Blank_A4.jrxml");
		view.setApplicationContext(appContext);

		Map<String, Object> params = new HashMap<>();
		params.put("datasource", admitCardGenService.findAll());

		return new ModelAndView(view, params);
	}
}
