package com.example.controller;

import com.example.controller.LoginController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Investment;
import com.example.model.User;
import com.example.service.InvestmentService;
import com.example.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InvestorController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvestmentService investmentService;
	
	@Autowired
    private LoginController loginController =  new LoginController();
	
	@RequestMapping(value="/admin/investor_make_investments", method = RequestMethod.GET)
	public ModelAndView investorMakeInvestment(){
		ModelAndView modelAndView = new ModelAndView();
		
		User user;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getName().contains("@")) {
			user = userService.findUserByEmail(auth.getName());
		}
		else {
			user = loginController.getSocialMediaUser();
		}
		
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Investors");
		modelAndView.setViewName("admin/investor_make_investments");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/investor_my_investments", method = RequestMethod.GET)
	public ModelAndView investorMyInvestment(){
		ModelAndView modelAndView = new ModelAndView();
		
		User user;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getName().contains("@")) {
			user = userService.findUserByEmail(auth.getName());
		}
		else {
			user = loginController.getSocialMediaUser();
		}
		
		
		List<Investment> investments  = investmentService.findByuserId(user.getId());
		
		
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Investors");
		
		//add Investment
		modelAndView.addObject("investments", investments);
		modelAndView.setViewName("/admin/investor_my_investments");
		
		
		/***TESTING INVESTMENT***/
		
		for(Investment x: investments) {
			System.out.println(x.getLatitude());
			System.out.println(x.getSpace());
		}
		
		
		return modelAndView;
	}
	
	
	
}
