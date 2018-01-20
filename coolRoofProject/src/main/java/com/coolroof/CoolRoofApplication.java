package com.coolroof;

/**
 * @author Michael Moser, Martin Schoenegger
 * The main class of our coolRoofApplication 
 * In the main method the whole application gets started
 **/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Client
public class CoolRoofApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoolRoofApplication.class, args);
	}
	
}
