package com.cart.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cart.api.dto.CartAction;
import com.cart.api.entity.Cart;
import com.cart.api.service.CartService;

import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<Cart> addProductToCart(@RequestParam Long userId,@RequestParam Long productId, @RequestParam int quantity){
		return ResponseEntity.ok(cartService.addProductToCart(userId, productId, quantity));
	}
	
	@DeleteMapping("/remove")

		public ResponseEntity<Cart> removeCartProduct(@RequestParam Long userId,@RequestParam Long productId) {
		return ResponseEntity.ok(cartService.removeCartProduct(userId, productId));
	}
	
	@GetMapping("/byuserid/{userId}")
	public ResponseEntity<Cart> getCart(@PathVariable Long userId){
		return ResponseEntity.ok(cartService.getCart(userId));	
	}
	
	@GetMapping("/audio")
	public ResponseEntity<String> audioToText(@RequestParam("file") MultipartFile file) throws IOException{
		return ResponseEntity.ok(cartService.audioToText(file));
	}
	
	@GetMapping("/process-audio")
	public ResponseEntity<Cart> processAudio(@PathVariable Long userId, @RequestParam("file")MultipartFile file) throws IOException{
		return ResponseEntity.ok(cartService.processAudio(userId, file));
	}

}
