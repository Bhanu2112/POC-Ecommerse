package com.cart.api.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cart implements Serializable {
	
	 private static  final long serialVersionUID = 214101981905645865L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CartItem> cartItems;
	
	
	

	public Cart() {
	
	}

	public Cart(Long id, Long userId, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.userId = userId;
		this.cartItems = cartItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
	    return "Cart{id=" + id + ", userId=" + userId + ", cartItemsSize=" + (cartItems != null ? cartItems.size() : 0) + "}";
	}

	
	
 
}
