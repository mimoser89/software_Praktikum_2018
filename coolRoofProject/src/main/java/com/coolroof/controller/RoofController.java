package com.coolroof.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coolroof.model.Roof;
import com.coolroof.model.User;
import com.coolroof.service.RoofService;
import com.coolroof.controller.LoginController;

@Controller
public class RoofController {

	@Autowired
	private RoofService roofService;
	@Autowired
	private LoginController lc = new LoginController();
	
	@RequestMapping(value="/admin/roofOwner", method = RequestMethod.GET)
	public ModelAndView roofOwner(){
		ModelAndView modelAndView = new ModelAndView();
	
		User user = lc.getLoggedInUser();
		
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Roof Owners");
		modelAndView.setViewName("admin/roofOwner");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/addRoof", method = RequestMethod.GET)
	public ModelAndView addRoof() {
		ModelAndView modelAndView = new ModelAndView();
	
		SecurityContextHolder.getContext().getAuthentication();
		
		Roof roof = new Roof();
		
		modelAndView.addObject("roof", roof);
		modelAndView.setViewName("admin/addRoof");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/addRoof", method = RequestMethod.POST)
	public ModelAndView createNewRoof(@Valid Roof roof, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		
		User user = lc.getLoggedInUser();
		
		// TODO check if roof is unique! in a better way
//		if (roofService.findRoofByAddress(roof.getAddress()) != null)
//			bindingResult.rejectValue("address", "error.roof", "This roof has already been registered");
		
		if (roof.getArea()<=0)
			bindingResult.rejectValue("area", "error.roof", "no polygon was drawnn");

		if (bindingResult.hasErrors()) {
			mav.setViewName("admin/addRoof");
		} else {
			roof.setUserId(user.getId());		// set roofOwner id
			roof.setPrice(10); 					// add fancy price calculation
			roof.setAreaLeft(roof.getArea());	// new roof, so arealeft = area
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
		
		SecurityContextHolder.getContext().getAuthentication();
	
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/owner_my_roofs", method = RequestMethod.GET)
	public ModelAndView myRoofs(){
		ModelAndView modelAndView = new ModelAndView();
	
		User user=lc.getLoggedInUser();
		
		List<Roof> myroofs  = roofService.findAllRoofsByUserId(user.getId());
	
		modelAndView.addObject("myroofs", myroofs);
		modelAndView.setViewName("admin/owner_my_roofs");
		
		return modelAndView;
	}
	
}
