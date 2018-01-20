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
	
	
	/**
	 * mapping handler method for /
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		
		List<Investment> investments = investmentService.findAll();
		
		User loggedIn = lc.getLoggedInUser();
		
		if(loggedIn != null) {
			modelAndView.addObject("welcome", "Welcome, " + loggedIn.getName() + " " + loggedIn.getLastName());
		}
		
		double co2saved = 0;

		for (Investment x : investments)
			co2saved += UtilClass.Co2Calc.calcCo2(x.getInvestmentDate().getTime(), Integer.parseInt(x.getSpace()));
		
		System.out.println(co2saved);
		modelAndView.addObject("co2Saved","CO2 saved: <br>" +  co2saved + " Tonne(n)");
		modelAndView.addObject("points",getAllCoordinates());
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
