package com.login.service.poc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.login.service.poc.dto.UserDTO;





@FeignClient(name="register-service",path = "/user")
public interface UserServiceClient {

	@GetMapping("/byusername/{username}")
	ResponseEntity<UserDTO> findByUsername(@PathVariable String username);
}
