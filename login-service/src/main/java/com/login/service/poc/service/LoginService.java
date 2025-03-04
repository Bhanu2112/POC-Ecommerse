package com.login.service.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.login.service.poc.config.UserDetailsImpl;
import com.login.service.poc.config.UserDetailsServiceImpl;
import com.login.service.poc.dto.LoginRequest;

@Service
public class LoginService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	
	public String login(LoginRequest loginRequest) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			String jwt = jwtService.generateToken(userDetails.getUsername());
			return jwt;
			
		}catch (Exception e) {
			// TODO: handle exception
			
			return "Incorrect username or password";
		}
	}

}
