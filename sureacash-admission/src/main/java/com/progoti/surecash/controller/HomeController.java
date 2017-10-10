package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.service.AdmissionService;
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = null;
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetailsImpl) {
				user = (UserDetailsImpl) principal;
				System.out.println(user.getUser().getUserName() + "-->" + user.getUser().getUniv().getId());
			}
		}
		System.out.println(user.getUser().getUserName() + "-->" + user.getUser().getUniv().getId());
		model.addAttribute("submitted", false);
		return "general_enquiry";
	}

	@GetMapping(value = "/edit-profile")
	public String editProfile(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = null;
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetailsImpl) {
				user = (UserDetailsImpl) principal;
				System.out.println(user.getUser().getUserName() + "-->" + user.getUser().getUniv().getId());
			}
		}
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

	@GetMapping("/application")
	public String showApplicationPage(Model model) {
		List<UnitDto> availableUnits = new ArrayList<>();
		List<UnitDto> appliedUnits = new ArrayList<>();

		availableUnits.add(
				new UnitDto(1, 11, "Physical Education and Sports Science", "E", "demo", "ABC University", "550.00"));
		availableUnits
				.add(new UnitDto(2, 22, "Computer Science and Engineering", "E", "demo", "ABC University", "950.00"));
		availableUnits
				.add(new UnitDto(3, 33, "Accounting and Information Systems", "F", "demo", "ABC University", "450.00"));
		availableUnits.add(
				new UnitDto(4, 44, "Electrical and Electronics Engineering", "E", "demo", "ABC University", "850.00"));

		appliedUnits.add(new UnitDto(5, 55, "English and Linguistics", "B", "demo", "ABC University", "150.00"));
		appliedUnits
				.add(new UnitDto(6, 66, "Philosophy, Politics and Economics", "A", "demo", "ABC University", "250.00"));
		appliedUnits.add(new UnitDto(7, 77, "Clothing and Textile Sciences", "C", "demo", "ABC University", "350.00"));
		appliedUnits.add(new UnitDto(8, 88, "Geographic Information Systems", "D", "demo", "ABC University", "400.00"));

		model.addAttribute("availableUnits", availableUnits);
		model.addAttribute("appliedUnits", appliedUnits);

		return "application/application.html";
	}

	@GetMapping("/payment")
	public String showPaymentPage(Model model) {
		List<PaymentRequestDto> paymentRequests = new ArrayList<>();

		paymentRequests.add(new PaymentRequestDto(1, "9307052", "F", "500.00", false));
		paymentRequests.add(new PaymentRequestDto(2, "0904090", "F", "500.00", true));
		paymentRequests.add(new PaymentRequestDto(3, "0904091", "F", "500.00", true));
		paymentRequests.add(new PaymentRequestDto(4, "9307052", "F", "500.00", false));
		paymentRequests.add(new PaymentRequestDto(5, "9307052", "F", "500.00", false));

		model.addAttribute("paymentRequests", paymentRequests);

		return "payment/payment.html";
	}
}
