package com.login.service.poc.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.login.service.poc.dto.UserDTO;



public class UserDetailsImpl implements UserDetails {

	 private String username;
	    private String password;
	    private List<GrantedAuthority> authorities;

	    public UserDetailsImpl(UserDTO user){
	        this.username = user.getUsername();
	        this.password = user.getPassword();
	        this.authorities = Stream.of(user.getRole()).map(x -> new SimpleGrantedAuthority("ROLE_" + x.name()))
	                .collect(Collectors.toList());
	        
	        System.out.println(this.authorities);
	        System.out.println(password);
	    }
	
	@Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities; }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	  @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }


}
