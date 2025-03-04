package com.register.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String street;
	private String city;
	private String state;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;


	public Address() {
		super();
		// TODO Auto-generated constructor stub
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Address(Long id, String street, String city, String state, User user) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.user = user;
	}


	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", user=" + user
				+ "]";
	}
	
	
	
}
