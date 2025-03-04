package com.cart.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class CartItem implements Serializable{
	 private static  final long serialVersionUID = 214101981905645865L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private Long productId;
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public CartItem(Long id, Long productId, int quantity, Cart cart) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.cart = cart;
	}

	public CartItem() {
		
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", cart=" + cart + "]";
	}
    

	
	
    
    
    

}
