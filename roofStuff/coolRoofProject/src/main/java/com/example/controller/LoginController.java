package com.example.controller;

import java.util.LinkedHashMap;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.service.UserService;

@Controller
public class LoginController {
	
	private String socialEmail;
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/selectRole", method = RequestMethod.GET)
	public ModelAndView selectRole(String email){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("selectRole");
		return modelAndView;
	}
	
	@RequestMapping(value = "/selectRole", method = RequestMethod.POST)
	public ModelAndView createUpdateSocialUser(@Valid User user, BindingResult bindingResult) {
		
		User userExists = userService.findUserByEmail(socialEmail);
		userExists.setRole(user.getRole());
		userService.saveUser(userExists);
		
		return new ModelAndView("redirect:/default");
	}
	
	
	@RequestMapping("/default")
	public String defaultAfterLogin() {
	     
		User user;
		
		Authentication authIsSocial = SecurityContextHolder.getContext().getAuthentication();
		
		if(authIsSocial.getName().contains("@")) {
			user = userService.findUserByEmail(authIsSocial.getName());
		}
		else {

			user = getSocialMediaUser();
			if(user.getRole().equals("notdefined")) {
				socialEmail = user.getEmail();
				return "redirect:/selectRole.html";
			}
		}
			
		if (user.getRole().equals("roofOwner")) {
			return "redirect:/admin/roofOwner.html";
	    }
	    
		return "redirect:/admin/investor_make_investments.html";
	}
	
	public User getSocialMediaUser() {
		
		User user;
		String socialMail;
		
		OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		Object login = auth.getUserAuthentication().getDetails();
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String,String> userData = (LinkedHashMap<String,String>)login;
		auth.setAuthenticated(true);
		
		if(userData.get("sub")==null) {
			socialMail = userData.get("id");
			socialMail += "@socialMediaAccount.com";
			
			user = userService.findUserByEmail(socialMail);
		}
		else {
			socialMail = userData.get("sub");
			socialMail += "@socialMediaAccount.com";
			
			user = userService.findUserByEmail(socialMail);
		}
		
		return user;
	}

	public User getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getName().contains("@"))
			return userService.findUserByEmail(auth.getName());
		else
			return getSocialMediaUser();
	}

	

}
