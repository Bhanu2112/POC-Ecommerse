package com.cart.api.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.api.dto.Product;


@FeignClient(name="product-service")
public interface ProductServiceClient {

	
	@GetMapping("/product/byid/{id}")
    Product getProductById(@PathVariable("id") Long id);
	 @GetMapping("/name/{name}")
	 public Product getProductByName(@PathVariable String name);
}
