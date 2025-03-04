package com.register.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.service.dto.RegisterRequest;
import com.register.service.entity.Address;
import com.register.service.entity.Role;
import com.register.service.entity.User;
import com.register.service.repository.UserRepository;

import jakarta.persistence.EnumType;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	 public User registerUser(RegisterRequest request) {
	        

	        // Create a new User entity
	        User user = new User();
	        user.setUsername(request.getUsername());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode the password
	       // user.setPassword(request.getPassword()); // Store plaintext password
	        user.setRole(Role.valueOf(request.getRole()));

	        // Map addresses from DTOs to entities
	        List<Address> addresses = request.getAddresses().stream()
	                .map(addressDTO -> {
	                    Address address = new Address();
	                    address.setStreet(addressDTO.getStreet());
	                    address.setCity(addressDTO.getCity());
	                    address.setState(addressDTO.getState());
	                    address.setUser(user);
	                    return address;
	                })
	                .collect(Collectors.toList());

	        user.setAddress(addresses);


	        return userRepository.save(user);
	    }
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with given username"));
	}
	
	

}
