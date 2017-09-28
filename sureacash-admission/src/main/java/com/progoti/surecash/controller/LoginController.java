package com.progoti.surecash.controller;

import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.service.ApplicationSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.progoti.surecash.admission.service.UserLoginService;

import javax.validation.Valid;

@Controller
public class LoginController {
	
//	@Autowired
	private UserLoginService userService;

	@Autowired
	private ApplicationSubmitService applicationSubmitService;

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public ModelAndView registration(@Valid ApplicationFormRequest formRequest){
		ModelAndView modelAndView = new ModelAndView();
        CredentialResponse response = applicationSubmitService.submitForm(formRequest);
        modelAndView.addObject("credential", response);
		modelAndView.setViewName("confirmation");
		return modelAndView;
	}
	
	/*
	 * @RequestMapping(value = "/registration", method = RequestMethod.POST) public
	 * ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	 * ModelAndView modelAndView = new ModelAndView(); User userExists =
	 * userService.findUserByEmail(user.getEmail()); if (userExists != null) {
	 * bindingResult .rejectValue("email", "error.user",
	 * "There is already a user registered with the email provided"); } if
	 * (bindingResult.hasErrors()) { modelAndView.setViewName("registration"); }
	 * else { userService.saveUser(user); modelAndView.addObject("successMessage",
	 * "User has been registered successfully"); modelAndView.addObject("user", new
	 * User()); modelAndView.setViewName("registration");
	 *
	 * } return modelAndView; }
	 */
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//User user = userService.findUserByEmail(auth.getName());
		//modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}
