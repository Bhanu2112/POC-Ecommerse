package com.register.service.dto;

import java.util.List;

import com.register.service.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class RegisterRequest {

	private String username;
    private String email;
    private String password;
    
    
    private String role;
    private List<AddressDTO> addresses;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<AddressDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterRequest(String username, String email, String password, String role, List<AddressDTO> addresses) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.addresses = addresses;
	}
	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", addresses=" + addresses + "]";
	}
    
    
}
