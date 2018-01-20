package com.coolroof.controller;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 * A Controller Class for Facebook and Google Login
 * While logging in this Controller checks if the 
 * Social Media Account is already in use
 * if not it creates a new user account connected to
 * the social media account 
 * 
 **/

import java.security.Principal;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coolroof.model.User;
import com.coolroof.service.UserService;

@SpringBootApplication
@RestController
@EnableOAuth2Client
public class SocialMediaController {

	@Autowired
	private UserService userService;

	/**
	 * @param a principal object containing user data
	 * @return the principal object
	 * this method creates a new user in our aws database
	 * if a user tries to log in with his Facebook or Google account 
	 * and is not already registered at our application
	 **/
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		
		String email, lastName, name;

		if (principal == null)
			return principal;

		OAuth2Authentication auth = (OAuth2Authentication) principal;
		Object login = auth.getUserAuthentication().getDetails();
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> userData = (LinkedHashMap<String, String>) login;
		auth.setAuthenticated(true);

		if (userData.get("sub") == null) {
			//user data for Facebook account
			email = userData.get("id");
			email += "@socialMediaAccount.com";
			Object[] temp = userData.get("name").split(" ");
			name = temp[0].toString();
			lastName = temp[1].toString();
		} else {
			//user data for Google Account
			email = userData.get("sub");
			email += "@socialMediaAccount.com";
			name = userData.get("given_name");
			lastName = userData.get("family_name");
		}

		User userExists = userService.findUserByEmail(email);

		if (userExists == null) {
			User newUser = new User();

			newUser.setEmail(email);
			newUser.setPassword("");
			newUser.setName(name);
			newUser.setLastName(lastName);
			newUser.setStreet("notdefined");
			newUser.setZipCode("notdefined");
			newUser.setCity("notdefined");
			newUser.setCountry("notdefined");
			newUser.setActive(1);
			newUser.setRole("notdefined");

			userService.saveUser(newUser);

		}
		
		return principal;
	}

}
