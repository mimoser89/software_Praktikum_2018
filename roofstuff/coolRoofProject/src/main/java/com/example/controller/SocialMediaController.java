package com.example.controller;

import java.security.Principal;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@SpringBootApplication
@RestController
@EnableOAuth2Client
public class SocialMediaController {
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		
		if(principal == null) return principal;
		OAuth2Authentication auth = (OAuth2Authentication) principal;
		Object login = auth.getUserAuthentication().getDetails();
		@SuppressWarnings("unchecked")
		LinkedHashMap<String,String> userData = (LinkedHashMap<String,String>)login;
		auth.setAuthenticated(true);
		
		String email;
		String lastName;
		String name;
		
		//sub is only exisiting in google
		if(userData.get("sub")==null) {
			
			email = userData.get("id");
			email += "@socialMediaAccount.com";
			Object[] temp = userData.get("name").split(" ");
			
			name = temp[0].toString();
			lastName = temp[1].toString();	
		}
		else {
			
			System.out.println("google");
			System.out.println(userData.get("sub"));
			
			email = userData.get("sub");
			email+="@socialMediaAccount.com";
			name = userData.get("given_name");
			lastName = userData.get("family_name");
		}
		
		
		User userExists = userService.findUserByEmail(email);
		
		if (userExists != null) {
			System.out.println("Gibts schau");
		}
		else {
			
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
			
			System.out.println("Wurde geaddet");
			
		}
		
		return principal;
	}
	
}
