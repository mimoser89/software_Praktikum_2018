package com.coolroof.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 * A controller Class for normally registered users
 * Methods for Login, Registration and for role selection included
 *
 **/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.coolroof.model.Investment;
import com.coolroof.model.Roof;
import com.coolroof.model.User;
import com.coolroof.service.InvestmentService;
import com.coolroof.service.RoofService;

@Controller
public class HomeController {
	
	@Autowired
	private InvestmentService investmentService;
	
	@Autowired
	private RoofService roofService;
	
	@Autowired
	private LoginController lc = new LoginController();
	
	//needed for social Media user
	private int firstLogin = 0;
	
	private String role;
	
	
	/**
	 * mapping handler method for /
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView();
		
		double co2SavedTotal = 0.0;
		
		List<Investment> investments = investmentService.findAll();
		
		User loggedIn = lc.getLoggedInUser();
		
		if(loggedIn != null) {
			modelAndView.addObject("welcome", "Go to personal page, " + loggedIn.getName() + " " + loggedIn.getLastName());
			firstLogin += 1;
			role = loggedIn.getRole();
			
		}
		else {
			firstLogin = 0;
		}
		
		for (Investment x : investments)
			co2SavedTotal += UtilClass.Co2Calc.calcCo2(x.getInvestmentDate().getTime(), Integer.parseInt(x.getSpace()));
		
		modelAndView.addObject("co2Saved","CO2 saved: <br>" +  Math.round(co2SavedTotal) + " Tonne(n)");
		modelAndView.addObject("points", getAllCoordinates());
		modelAndView.addObject("firstLogin",firstLogin);
		modelAndView.addObject("role",role);
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
	private ArrayList<Point2D> getAllCoordinates() {
		
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
		List<Roof> roofs = roofService.findAll();
		
		for(Roof x: roofs) {
			if(x.getArea() != x.getAreaLeft()) {
				Point2D temp = new Point2D.Double();
				temp.setLocation(Double.parseDouble(x.getLatitude()), Double.parseDouble(x.getLongitude()));
				points.add(temp);
			}		
				
		}
		
		return points;
	}
	
}