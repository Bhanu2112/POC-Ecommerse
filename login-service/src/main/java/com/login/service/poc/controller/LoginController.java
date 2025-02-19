package com.login.service.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.service.poc.dto.LoginRequest;
import com.login.service.poc.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(loginService.login(request));
	}

}
