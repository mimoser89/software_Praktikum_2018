package com.coolroof.controller;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 * A controller Class for normally registered users
 * Methods for Login, Registration and for role selection included
 *
 **/

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

import com.coolroof.model.User;
import com.coolroof.service.UserService;

@Controller
public class LoginController {

	private String socialEmail;

	@Autowired
	private UserService userService;

	/**
	 * mapping handler method for / and /login
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	/**
	 * mapping handler method for /registration
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	/**
	 * mapping handler method for /registration handles the submission of a user
	 * registration
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		}else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	/**
	 * mapping handler method for selecting a role this occurs only at the
	 * registration or at the very first login of a social media user
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/selectRole", method = RequestMethod.GET)
	public ModelAndView selectRole(String email) {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("selectRole");
		return modelAndView;
	}

	/**
	 * mapping handler method for /select role finally selects the role and
	 * updates the information in the database
	 * 
	 * @return modelAndView
	 **/
	@RequestMapping(value = "/selectRole", method = RequestMethod.POST)
	public ModelAndView createUpdateSocialUser(@Valid User user, BindingResult bindingResult) {

		User userExists = userService.findUserByEmail(socialEmail);
		userExists.setRole(user.getRole());
		userService.saveUser(userExists);

		return new ModelAndView("redirect:/default");
	}

	/**
	 * mapping handler method for /default redirects right after login to
	 * roofOwner or make_investment page depending on the selected role of the
	 * user
	 * @return modelAndView
	 **/
	@RequestMapping("/default")
	public ModelAndView defaultAfterLogin() {

		ModelAndView modelAndView = new ModelAndView();
		
		User user;

		Authentication authIsSocial = SecurityContextHolder.getContext().getAuthentication();

		if (authIsSocial.getName().contains("@")) {
			user = userService.findUserByEmail(authIsSocial.getName());
		} else {

			user = getSocialMediaUser();
			
			//if no role exists, notdefined
			if (user.getRole().equals("notdefined")) {
				socialEmail = user.getEmail();
				modelAndView.setViewName("redirect:/selectRole.html");
				return modelAndView;
			}
		}
		
		if (user.getRole().equals("roofOwner")) {
			modelAndView.setViewName("redirect:/admin/roofOwner.html");
			return modelAndView;
			
		}
		modelAndView.setViewName("redirect:/admin/investor_make_investments.html");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/failurePage", method = RequestMethod.GET)
	public ModelAndView failurePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/failurePage");
		return modelAndView;
		
	}

	public User getSocialMediaUser() {

		User user;
		String socialMail;

		OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		Object login = auth.getUserAuthentication().getDetails();

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> userData = (LinkedHashMap<String, String>) login;
		auth.setAuthenticated(true);

		if (userData.get("sub") == null) {
			socialMail = userData.get("id");
			socialMail += "@socialMediaAccount.com";

			user = userService.findUserByEmail(socialMail);
			
		} else {
			socialMail = userData.get("sub");
			socialMail += "@socialMediaAccount.com";

			user = userService.findUserByEmail(socialMail);
		}

		return user;
	}
	
	public User getLoggedInUser() {
		Authentication auth	 = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal().toString().equals("anonymousUser")) return null;
	
		if (auth.getName().contains("@"))
			return userService.findUserByEmail(auth.getName());
		else
			return getSocialMediaUser();
	}

}
