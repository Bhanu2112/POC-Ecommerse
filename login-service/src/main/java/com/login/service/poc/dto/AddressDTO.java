package com.login.service.poc.dto;

public class AddressDTO {
	private Long id;

	private String street;
	private String city;
	private String state;
	
	
	
	public AddressDTO() {
		super();
	}
	public AddressDTO(Long id, String street, String city, String state) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + "]";
	}
	
	
	
	
}
