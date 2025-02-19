package com.login.service.poc.config;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.service.poc.client.UserServiceClient;
import com.login.service.poc.dto.UserDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserServiceClient userServiceClient;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDTO user = userServiceClient.findByUsername(username).getBody();
	        if (user != null) {
	            return 
	                   new UserDetailsImpl(user);
//	            Stream.of(user.getRole())
//        		.map(x -> new SimpleGrantedAuthority("ROLE_" + x.name()))
//                .collect(Collectors.toList())));
	        }
	        throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
