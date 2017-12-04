package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Roof;
import com.example.model.User;
import com.example.service.RoofService;
import com.example.service.UserService;
import com.example.controller.LoginController;

@Controller
public class RoofController {

	@Autowired
	private RoofService roofService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginController lc = new LoginController();
	
	@RequestMapping(value="/admin/roofOwner", method = RequestMethod.GET)
	public ModelAndView roofOwner(){
		ModelAndView modelAndView = new ModelAndView();
		User user;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getName().contains("@"))
			user = userService.findUserByEmail(auth.getName());
		else
			user = lc.getSocialMediaUser();
		
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Roof Owners");
		modelAndView.setViewName("admin/roofOwner");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/addRoof", method = RequestMethod.GET)
	public ModelAndView addRoof() {
		ModelAndView modelAndView = new ModelAndView();
		User user;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getName().contains("@"))
			user = userService.findUserByEmail(auth.getName());
		else
			user = lc.getSocialMediaUser();
		
		Roof roof = new Roof();
		
		modelAndView.addObject("roof", roof);
		modelAndView.setViewName("admin/addRoof");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/addRoof", method = RequestMethod.POST)
	public ModelAndView createNewRoof(@Valid Roof roof, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		User user;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getName().contains("@"))
			user = userService.findUserByEmail(auth.getName());
		else
			user = lc.getSocialMediaUser();

		if (roofService.findRoofByAddress(roof.getAddress()) != null)
			bindingResult.rejectValue("address", "error.roof", "This roof has already been registered");

		if (bindingResult.hasErrors()) {
			mav.setViewName("admin/addRoof");
		} else {
			roof.setUserId(user.getId());
			roofService.saveRoof(roof);
			mav.addObject("successMessage", "Roof has been registered successfully");
			mav.addObject("roof", new Roof());
			mav.setViewName("admin/roofSuccess");
		}
		return mav;
	}
	
	@RequestMapping(value = "/admin/roofSuccess", method = RequestMethod.GET)
	public ModelAndView roofSuccess() {
		
		ModelAndView modelAndView = new ModelAndView();
	
		return modelAndView;
	}

}
