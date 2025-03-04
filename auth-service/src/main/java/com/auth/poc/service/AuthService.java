package com.auth.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth.poc.dto.LoginRequest;
import com.auth.poc.dto.TokenDTO;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTService jwtService;

	public TokenDTO login(LoginRequest request) {

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		if (authenticate.isAuthenticated()) {

			TokenDTO token = new TokenDTO();
			token.setToken(jwtService.generateToken(request.getUsername()));
			return token;
		}

		else {
			throw new RuntimeException("Wrong credentials");
		}
	}

}
