package com.poc.product.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product implements Serializable{
	private static  final long serialVersionUID = 214101981905645865L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName;
	private double price;
	private String category;
	private boolean isDeleted;
	
	
	public Product() {
		
	}
	
	
	public Product(Long id, String productName, double price, String category, boolean isDeleted) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.isDeleted = isDeleted;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}


	public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", price=" + price + ", category=" + category
				+ ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	

}
