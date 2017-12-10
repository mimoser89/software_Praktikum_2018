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
	
	private int userID;
	
	private List<Roof> roofs;
	
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
		
		roofs = roofService.findAll();
		
		Set<String> regions = new HashSet<String>();
		
		for(Roof x: roofs) {
			regions.add(x.getRegion());
		}
		
		Investment investment = new Investment();
		
		modelAndView.addObject("investment", investment);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content for Investors");
		modelAndView.addObject("regions", regions);
		modelAndView.addObject("roofs", roofs);
		modelAndView.setViewName("admin/investor_make_investments");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/investor_make_investments", method = RequestMethod.POST)
	public ModelAndView createNewInvestment(@Valid Investment investment){
		ModelAndView modelAndView = new ModelAndView();
		
		int magnitude = 0;
		
		investment.setUserId(userID);
		int[][] splittedValues = getValuesOfInvestment(investment.getRoofIds());
		
		//splittedValues[i][0] == ROOFID
		//splittedValues[i][1] == OCCUPIEDSPACE
		//TODO: Actually update the values in the database 
		for(int i = 0; i < splittedValues.length; i++){
			for(int j = 0; j < splittedValues[i].length; j++){
				System.out.print(splittedValues[i][j] + " ");
			}
			System.out.println();
		}
		
		//update roof remaining values
		for(Roof x: roofs) {
			for(int i = 0; i < splittedValues.length; i++) {
				//check for same roof ID
				if(x.getRoofId() == splittedValues[i][0]) {
					//update area left in roof
					x.setAreaLeft(x.getAreaLeft() - splittedValues[i][1]);
					roofService.saveRoof(x);
				}
			}
		}
		
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
		
		return modelAndView;
	}
	
	
	// returns an n x 2 array of roofId and occupied space
	private int[][] getValuesOfInvestment(String roofIds){
		
		int[][] actualValues;
		String[] temp, firstSplit = roofIds.split(";");
		actualValues = new int[firstSplit.length][2];
		for(int i = 0; i < firstSplit.length; i++){
			temp = firstSplit[i].split(",");
			actualValues[i][0] =  Integer.parseInt(temp[0]);
			actualValues[i][1] =  Integer.parseInt(temp[1]);		
		}
	
		return actualValues;
	}
}
