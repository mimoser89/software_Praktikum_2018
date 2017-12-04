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
import com.example.model.Roof;
import com.example.model.User;
import com.example.service.InvestmentService;
import com.example.service.RoofService;
import com.example.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;


@Controller
public class InvestorController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvestmentService investmentService;
	
	@Autowired
	private RoofService roofService;
	
	@Autowired
    private LoginController loginController =  new LoginController();
	
	int userID;
	
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
		
		userID = user.getId();
		
		List<Roof> temp = roofService.findAll();
		
		Set<String> regions = new HashSet<String>();
		
		for(Roof x: temp) {
			regions.add(x.getRegion());
		}
		
		Investment investment = new Investment();
		
		modelAndView.addObject("investment", investment);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Investors");
		modelAndView.addObject("regions", regions);
		modelAndView.setViewName("admin/investor_make_investments");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/investor_make_investments", method = RequestMethod.POST)
	public ModelAndView createNewInvestment(@Valid Investment investment){
		ModelAndView modelAndView = new ModelAndView();
		
		investment.setUserId(userID);
		investment.setLongitude("Longitude");
		investment.setLatitude("Latidude");
		investment.setStatus("good");
		investment.setMagnitude(1);
		
		System.out.println(investment.getRegion());
		System.out.println(investment.getLatitude());
		System.out.println(investment.getLongitude());
		System.out.println(investment.getMagnitude());
		
		investmentService.saveInvestment(investment);
		
		modelAndView.setViewName("admin/investor_make_investments");
		
		return new ModelAndView("redirect:/admin/investor_my_investments");
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
		}
		return modelAndView;
	}
	
	
	
}
