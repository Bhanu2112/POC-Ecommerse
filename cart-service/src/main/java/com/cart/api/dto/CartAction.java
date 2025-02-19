package com.cart.api.dto;

public class CartAction {
	private String action;  // "add" or "remove"
    private String productName;
    private int quantity;

    public CartAction(String action, String productName, int quantity) {
        this.action = action;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getAction() { return action; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
}
