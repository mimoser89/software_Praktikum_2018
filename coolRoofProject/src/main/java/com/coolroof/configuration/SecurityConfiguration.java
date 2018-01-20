package com.coolroof.configuration;

/**
 * @author Michael Moser, Martin Schoenegger
 * The overall security configuration for the coolRoof application
 * This class also contains settings for the Facebook and Google login
 **/

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final String INVESTOR = "investor";
	private final String ROOFOWNER = "roofOwner";
	private org.slf4j.Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);	
	}

	/**
	 * @param HttpSecurity http requires the user to be of role user
	 * contains information about how to authenticate our users
	 * 
	 **/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http.antMatcher("/**")
		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
		.authorizeRequests()
			.antMatchers("/", "/connect**", "/webjars/**", "/login*", "/registration", "/selectRole", "/access-denied").permitAll()
			.antMatchers("/admin/investor_make_investments*", "/admin/investor_my_investments*").authenticated()
			.antMatchers("/admin/addRoof*", "/admin/owner_my_roofs*", "/admin/roofOwner.html", "/admin/roofSuccess*").authenticated()
			.and().formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/default")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .logoutSuccessUrl("/").and().csrf()
		    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		
   }
		
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers(/*"/resources/**", "/static/**",*/ "/css/**", "/js/**" );
	}
	
	/**
	 * A simple ssoFilter for Facebook and Google login
	 * the application needs to know how to connect with Facebook
	 * and Google
	 **/
	private Filter ssoFilter() {

		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();

		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/connect/facebook");
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
				facebook().getClientId());
		tokenServices.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(tokenServices);

		OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/connect/google");
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
		tokenServices.setRestTemplate(googleTemplate);
		googleFilter.setTokenServices(tokenServices);

		filters.add(facebookFilter);
		filters.add(googleFilter);
	
		filter.setFilters(filters);

		return filter;
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}

	@Bean
	@ConfigurationProperties("google.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("google.resource")
	public ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}

}