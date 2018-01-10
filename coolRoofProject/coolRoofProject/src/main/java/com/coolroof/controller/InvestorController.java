package com.coolroof.controller;

/**
 * @author Michael Moser, Martin Schoenegger
 * A controller class for investors
 * Contains methods for making, creating and displaying the investments
 * all these methods are interacting with the aws database
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coolroof.controller.LoginController;
import com.coolroof.model.Investment;
import com.coolroof.model.Roof;
import com.coolroof.model.User;
import com.coolroof.service.InvestmentService;
import com.coolroof.service.RoofService;
import com.coolroof.service.UserService;

import groovyjarjarcommonscli.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
	private LoginController lc = new LoginController();

	private int userID;

	private List<Roof> roofs;

	/**
	 * retrieves information from the database setting up everything so the user
	 * can make his investment the data gets passed to the frontend == get
	 * request
	 **/

	@RequestMapping(value = "/admin/investor_make_investments", method = RequestMethod.GET)
	public ModelAndView investorMakeInvestment() {
		ModelAndView modelAndView = new ModelAndView();

		User user = lc.getLoggedInUser();

		if(!user.getRole().equals("investor")) {
			modelAndView.setViewName("redirect:/admin/failurePage.html");
			return modelAndView;
		}

		userID = user.getId();

		roofs = roofService.findAll();

		Set<String> regions = new HashSet<String>();

		for (Roof x : roofs) {
			regions.add(x.getRegion());
		}

		Investment investment = new Investment();

		modelAndView.addObject("investment", investment);
		modelAndView.addObject("makeYourInvestment", "Make your investment, " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("welcome", "Welcome, " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage", "Content for Investors");
		modelAndView.addObject("regions", regions);
		modelAndView.addObject("roofs", roofs);
		modelAndView.setViewName("admin/investor_make_investments");

		return modelAndView;
	}

	/**
	 * makes the new investment; calculates the magnitude of the investment and
	 * updates the roof values updates all the data in aws redirects to my
	 * investment
	 **/
	@RequestMapping(value = "/admin/investor_make_investments", method = RequestMethod.POST)
	public ModelAndView createNewInvestment(@Valid Investment investment) {
		ModelAndView modelAndView = new ModelAndView();

		int magnitude = 0;

		investment.setUserId(userID);
		
		// splittedValues[i][0] == ROOFID
		// splittedValues[i][1] == OCCUPIEDSPACE
		int[][] splittedValues = getValuesOfInvestment(investment.getRoofIds());

		// update roof remaining values and calculate magnitude of investment
		for (Roof x : roofs) {
			for (int i = 0; i < splittedValues.length; i++) {
				// check for same roof ID
				if (x.getRoofId() == splittedValues[i][0]) {
					
					// update area left in roof
					x.setAreaLeft(x.getAreaLeft() - splittedValues[i][1]);
					
					// calculate magnitude
					magnitude += x.getPrice() * splittedValues[i][1];
					roofService.saveRoof(x);

				}
			}
		}
	
		investment.setMagnitude(magnitude);
		investmentService.saveInvestment(investment);

		modelAndView.setViewName("admin/investor_make_investments");

		return new ModelAndView("redirect:/admin/investor_my_investments");
	}

	/**
	 * shows the my investment page checks if user is authenticated passes a
	 * list of all the users investments to the frontend also passes a welcome
	 * message to the frontend
	 **/
	@RequestMapping(value = "/admin/investor_my_investments", method = RequestMethod.GET)
	public ModelAndView investorMyInvestment() {
		ModelAndView modelAndView = new ModelAndView();

		User user = lc.getLoggedInUser();
		double co2SavedTotal;
		double rtnOfInterest;
		double constPaybackFactor = 0.1;
		
		if(!user.getRole().equals("investor")) {
			modelAndView.setViewName("redirect:/admin/failurePage.html");
			return modelAndView;
		}

		List<Investment> investments = investmentService.findByuserId(user.getId());

		for (Investment x : investments) {
			x.setValuesOfInvestment(getValuesOfInvestment(x.getRoofIds()));
		}
		
		
		co2SavedTotal = calculateCO2SavedTotal(investments);
		rtnOfInterest = co2SavedTotal * constPaybackFactor;
		
		modelAndView.addObject("welcome", "Welcome, " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("co2Saved", "CO2 saved in total: " + co2SavedTotal + " Tonnen");
		modelAndView.addObject("rtnOfInterest", "Return of interests: " + rtnOfInterest+ " Euro");
		
		// add Investment
		modelAndView.addObject("investments", investments);
		modelAndView.addObject("roofs", roofs);
		modelAndView.setViewName("/admin/investor_my_investments");

		return modelAndView;
	}

	public double calculateCO2SavedTotal(List<Investment> investments)  {
		
		double constCO2Factor = 0.1;
		Date d1 = new Date();
		Date d2;
		long diff,duration;
		double sum=0;
		
		
		for(int i=0; i<investments.size();i++) {
			d2 = investments.get(i).getInvestmentDate();
			diff = d1.getTime() - d2.getTime();
			duration = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			sum += duration * Integer.parseInt(investments.get(i).getSpace());
		}
		
		System.out.println(sum);
		return sum * constCO2Factor;
	    	
	}
	
	// helper method
	// returns an n x 2 array of roofId and occupied space
	private int[][] getValuesOfInvestment(String roofIds) {

		int[][] actualValues;
		String[] temp, firstSplit = roofIds.split(";");
		actualValues = new int[firstSplit.length][2];
		for (int i = 0; i < firstSplit.length; i++) {
			temp = firstSplit[i].split(",");
			actualValues[i][0] = Integer.parseInt(temp[0]);
			actualValues[i][1] = Integer.parseInt(temp[1]);
		}

		return actualValues;
	}
}
