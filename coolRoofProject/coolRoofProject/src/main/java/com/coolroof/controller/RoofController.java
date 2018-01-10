package com.coolroof.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	// mapping country code by gmaps to continent
	private final static Map<String, String> COUNTRY2CONTINENT = createMap();

	// map initializing
	private static Map<String, String> createMap() {
		Map<String, String> map = new HashMap<>();
		map.put("A1", "Other");
		map.put("A2", "Other");
		map.put("AD", "Europe");
		map.put("AE", "Asia");
		map.put("AF", "Asia");
		map.put("AG", "North America");
		map.put("AI", "North America");
		map.put("AL", "Europe");
		map.put("AM", "Asia");
		map.put("AN", "North America");
		map.put("AO", "Africa");
		map.put("AP", "Asia");
		map.put("AQ", "Antarctica");
		map.put("AR", "South America");
		map.put("AS", "Australia/Oceania");
		map.put("AT", "Europe");
		map.put("AU", "Australia/Oceania");
		map.put("AW", "North America");
		map.put("AX", "Europe");
		map.put("AZ", "Asia");
		map.put("BA", "Europe");
		map.put("BB", "North America");
		map.put("BD", "Asia");
		map.put("BE", "Europe");
		map.put("BF", "Africa");
		map.put("BG", "Europe");
		map.put("BH", "Asia");
		map.put("BI", "Africa");
		map.put("BJ", "Africa");
		map.put("BL", "North America");
		map.put("BM", "North America");
		map.put("BN", "Asia");
		map.put("BO", "South America");
		map.put("BR", "South America");
		map.put("BS", "North America");
		map.put("BT", "Asia");
		map.put("BV", "AN");
		map.put("BW", "Africa");
		map.put("BY", "Europe");
		map.put("BZ", "North America");
		map.put("CA", "North America");
		map.put("CC", "Asia");
		map.put("CD", "Africa");
		map.put("CF", "Africa");
		map.put("CG", "Africa");
		map.put("CH", "Europe");
		map.put("CI", "Africa");
		map.put("CK", "Australia/Oceania");
		map.put("CL", "South America");
		map.put("CM", "Africa");
		map.put("CN", "Asia");
		map.put("CO", "South America");
		map.put("CR", "North America");
		map.put("CU", "North America");
		map.put("CV", "Africa");
		map.put("CX", "Asia");
		map.put("CY", "Asia");
		map.put("CZ", "Europe");
		map.put("DE", "Europe");
		map.put("DJ", "Africa");
		map.put("DK", "Europe");
		map.put("DM", "North America");
		map.put("DO", "North America");
		map.put("DZ", "Africa");
		map.put("EC", "South America");
		map.put("EE", "Europe");
		map.put("EG", "Africa");
		map.put("EH", "Africa");
		map.put("ER", "Africa");
		map.put("ES", "Europe");
		map.put("ET", "Africa");
		map.put("EU", "Europe");
		map.put("FI", "Europe");
		map.put("FJ", "Australia/Oceania");
		map.put("FK", "South America");
		map.put("FM", "Australia/Oceania");
		map.put("FO", "Europe");
		map.put("FR", "Europe");
		map.put("FX", "Europe");
		map.put("GA", "Africa");
		map.put("GB", "Europe");
		map.put("GD", "North America");
		map.put("GE", "Asia");
		map.put("GF", "South America");
		map.put("GG", "Europe");
		map.put("GH", "Africa");
		map.put("GI", "Europe");
		map.put("GL", "North America");
		map.put("GM", "Africa");
		map.put("GN", "Africa");
		map.put("GP", "North America");
		map.put("GQ", "Africa");
		map.put("GR", "Europe");
		map.put("GS", "AN");
		map.put("GT", "North America");
		map.put("GU", "Australia/Oceania");
		map.put("GW", "Africa");
		map.put("GY", "South America");
		map.put("HK", "Asia");
		map.put("HM", "AN");
		map.put("HN", "North America");
		map.put("HR", "Europe");
		map.put("HT", "North America");
		map.put("HU", "Europe");
		map.put("ID", "Asia");
		map.put("IE", "Europe");
		map.put("IL", "Asia");
		map.put("IM", "Europe");
		map.put("IN", "Asia");
		map.put("IO", "Asia");
		map.put("IQ", "Asia");
		map.put("IR", "Asia");
		map.put("IS", "Europe");
		map.put("IT", "Europe");
		map.put("JE", "Europe");
		map.put("JM", "North America");
		map.put("JO", "Asia");
		map.put("JP", "Asia");
		map.put("KE", "Africa");
		map.put("KG", "Asia");
		map.put("KH", "Asia");
		map.put("KI", "Australia/Oceania");
		map.put("KM", "Africa");
		map.put("KN", "North America");
		map.put("KP", "Asia");
		map.put("KR", "Asia");
		map.put("KW", "Asia");
		map.put("KY", "North America");
		map.put("KZ", "Asia");
		map.put("LA", "Asia");
		map.put("LB", "Asia");
		map.put("LC", "North America");
		map.put("LI", "Europe");
		map.put("LK", "Asia");
		map.put("LR", "Africa");
		map.put("LS", "Africa");
		map.put("LT", "Europe");
		map.put("LU", "Europe");
		map.put("LV", "Europe");
		map.put("LY", "Africa");
		map.put("MA", "Africa");
		map.put("MC", "Europe");
		map.put("MD", "Europe");
		map.put("ME", "Europe");
		map.put("MF", "North America");
		map.put("MG", "Africa");
		map.put("MH", "Australia/Oceania");
		map.put("MK", "Europe");
		map.put("ML", "Africa");
		map.put("MM", "Asia");
		map.put("MN", "Asia");
		map.put("MO", "Asia");
		map.put("MP", "Australia/Oceania");
		map.put("MQ", "North America");
		map.put("MR", "Africa");
		map.put("MS", "North America");
		map.put("MT", "Europe");
		map.put("MU", "Africa");
		map.put("MV", "Asia");
		map.put("MW", "Africa");
		map.put("MX", "North America");
		map.put("MY", "Asia");
		map.put("MZ", "Africa");
		map.put("NA", "Africa");
		map.put("NC", "Australia/Oceania");
		map.put("NE", "Africa");
		map.put("NF", "Australia/Oceania");
		map.put("NG", "Africa");
		map.put("NI", "North America");
		map.put("NL", "Europe");
		map.put("NO", "Europe");
		map.put("NP", "Asia");
		map.put("NR", "Australia/Oceania");
		map.put("NU", "Australia/Oceania");
		map.put("NZ", "Australia/Oceania");
		map.put("O1", "Other");
		map.put("OM", "Asia");
		map.put("PA", "North America");
		map.put("PE", "South America");
		map.put("PF", "Australia/Oceania");
		map.put("PG", "Australia/Oceania");
		map.put("PH", "Asia");
		map.put("PK", "Asia");
		map.put("PL", "Europe");
		map.put("PM", "North America");
		map.put("PN", "Australia/Oceania");
		map.put("PR", "North America");
		map.put("PS", "Asia");
		map.put("PT", "Europe");
		map.put("PW", "Australia/Oceania");
		map.put("PY", "South America");
		map.put("QA", "Asia");
		map.put("RE", "Africa");
		map.put("RO", "Europe");
		map.put("RS", "Europe");
		map.put("RU", "Europe");
		map.put("RW", "Africa");
		map.put("SA", "Asia");
		map.put("SB", "Australia/Oceania");
		map.put("SC", "Africa");
		map.put("SD", "Africa");
		map.put("SE", "Europe");
		map.put("SG", "Asia");
		map.put("SH", "Africa");
		map.put("SI", "Europe");
		map.put("SJ", "Europe");
		map.put("SK", "Europe");
		map.put("SL", "Africa");
		map.put("SM", "Europe");
		map.put("SN", "Africa");
		map.put("SO", "Africa");
		map.put("SR", "South America");
		map.put("ST", "Africa");
		map.put("SV", "North America");
		map.put("SY", "Asia");
		map.put("SZ", "Africa");
		map.put("TC", "North America");
		map.put("TD", "Africa");
		map.put("TF", "AN");
		map.put("TG", "Africa");
		map.put("TH", "Asia");
		map.put("TJ", "Asia");
		map.put("TK", "Australia/Oceania");
		map.put("TL", "Asia");
		map.put("TM", "Asia");
		map.put("TN", "Africa");
		map.put("TO", "Australia/Oceania");
		map.put("TR", "Europe");
		map.put("TT", "North America");
		map.put("TV", "Australia/Oceania");
		map.put("TW", "Asia");
		map.put("TZ", "Africa");
		map.put("UA", "Europe");
		map.put("UG", "Africa");
		map.put("UM", "Australia/Oceania");
		map.put("US", "North America");
		map.put("UY", "South America");
		map.put("UZ", "Asia");
		map.put("VA", "Europe");
		map.put("VC", "North America");
		map.put("VE", "South America");
		map.put("VG", "North America");
		map.put("VI", "North America");
		map.put("VN", "Asia");
		map.put("VU", "Australia/Oceania");
		map.put("WF", "Australia/Oceania");
		map.put("WS", "Australia/Oceania");
		map.put("YE", "Asia");
		map.put("YT", "Africa");
		map.put("ZA", "Africa");
		map.put("ZM", "Africa");
		map.put("ZW", "Africa");
		
		return map;
	}

	@RequestMapping(value = "/admin/roofOwner", method = RequestMethod.GET)
	public ModelAndView roofOwner() {
		ModelAndView modelAndView = new ModelAndView();

		User user = lc.getLoggedInUser();
		
		if(!user.getRole().equals("roofOwner")) {
			modelAndView.setViewName("redirect:/admin/failurePage.html");
			return modelAndView;
		}

		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage", "Content for Roof Owners");
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
		
		if(!user.getRole().equals("roofOwner")) {
			mav.setViewName("redirect:/admin/failurePage.html");
			return mav;
		}

		// TODO check if roof is unique
		
		if (roof.getArea() <= 0)
			bindingResult.rejectValue("area", "error.roof", "no polygon was drawnn");

		if (bindingResult.hasErrors()) {
			mav.setViewName("admin/addRoof");
		} else {
			
			// setting roofOwner-ID
			roof.setUserId(user.getId());
			
			// looking up continent corresponding to country
			roof.setRegion(COUNTRY2CONTINENT.get(roof.getRegion()));
			
			// TODO add fancy price calculation
			roof.setPrice(10); 
			
			// new roof, so areaLeft equals total area
			roof.setAreaLeft(roof.getArea());
			
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

	@RequestMapping(value = "/admin/owner_my_roofs", method = RequestMethod.GET)
	public ModelAndView myRoofs() {
		ModelAndView modelAndView = new ModelAndView();

		User user = lc.getLoggedInUser();
		
		if(!user.getRole().equals("roofOwner")) {
			modelAndView.setViewName("redirect:/admin/failurePage.html");
			return modelAndView;
		}

		List<Roof> myroofs = roofService.findAllRoofsByUserId(user.getId());

		modelAndView.addObject("myroofs", myroofs);
		modelAndView.setViewName("admin/owner_my_roofs");

		return modelAndView;
	}

}
