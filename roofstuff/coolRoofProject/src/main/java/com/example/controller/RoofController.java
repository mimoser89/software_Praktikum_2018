package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Roof;
import com.example.service.RoofService;

@Controller
public class RoofController {

	@Autowired
	private RoofService roofService;

	@RequestMapping(value = "/addRoof", method = RequestMethod.GET)
	public ModelAndView addRoof() {
		
		ModelAndView modelAndView = new ModelAndView();
		Roof roof = new Roof();
		modelAndView.addObject("roof", roof);
		modelAndView.setViewName("addRoof");
		return modelAndView;
	}

	@RequestMapping(value = "/addRoof", method = RequestMethod.POST)
	public ModelAndView createNewRoof(@Valid Roof roof, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		Roof roofExists = roofService.findRoofByAddress(roof.getAddress());
		System.out.println(roof.getAddress());
		if (roofExists != null) {
			bindingResult.rejectValue("address", "error.roof", "This roof has already been registered");
		}
		if (bindingResult.hasErrors()) {
			mav.setViewName("addRoof");
		} else {
			roofService.saveRoof(roof);
			mav.addObject("successMessage", "Roof has been registered successfully");
			mav.addObject("roof", new Roof());
			mav.setViewName("login");
		}
		return mav;
	}

}
