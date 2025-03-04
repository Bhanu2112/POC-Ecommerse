package com.auth.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.poc.dto.LoginRequest;
import com.auth.poc.dto.TokenDTO;
import com.auth.poc.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
		
		return ResponseEntity.ok(authService.login(request));
	//	return ResponseEntity.ok(new TokenDTO());
	}
	
	
	@GetMapping("/welcome")
	public ResponseEntity<String> welcome(){
		return ResponseEntity.ok("Welcome");
	}

}
