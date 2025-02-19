package com.register.service.dto;

public class AddressDTO {
	 private String street;
	    private String city;
	    private String state;
	   
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
		
		public AddressDTO(String street, String city, String state) {
			super();
			this.street = street;
			this.city = city;
			this.state = state;
			
		}
		public AddressDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "AddressDTO [street=" + street + ", city=" + city + ", state=" + state 
					+ "]";
		}
	    
	    
}
