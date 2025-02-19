package com.auth.poc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.poc.client.UserServiceClient;
import com.auth.poc.dto.UserDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserServiceClient userServiceClient;
	


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println("inside load username method");
		UserDTO user = userServiceClient.findByUsername(username).getBody();
		System.out.println(user);
		//String password = "{bcrypt}"+user.getPassword();
		//user.setPassword(password);
		
		
		
		return new CustomUserDetails(user)
				;
	}

}
