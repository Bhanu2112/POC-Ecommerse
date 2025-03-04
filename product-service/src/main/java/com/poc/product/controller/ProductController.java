package com.poc.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.product.entity.Product;
import com.poc.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/add")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		
		System.out.println("----- add product");
	        return ResponseEntity.ok(productService.addProduct(product));
	  }

	 @DeleteMapping("/remove/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
	        productService.softDeleteProduct(id);
	        return ResponseEntity.ok("Product soft deleted");
	    }
	 
	 @GetMapping("/name/{name}")
	 public ResponseEntity<Product> getProductByName(@PathVariable String name){
		 return ResponseEntity.ok(productService.getProductByName(name));
	 }
	 
	 @PutMapping("/update-price/{id}")
	 //@PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @RequestParam Double newPrice) {
	        return ResponseEntity.ok(productService.updateProductPrice(id, newPrice));
	    }
	 
	 @GetMapping("/category/{category}")
	 public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category){
		 return ResponseEntity.ok(productService.getProductsByCategory(category));
	 }
	 
	 @GetMapping("/price/asc")
	 public ResponseEntity<List<Product>> getProductsSortedByAsc(){
		 return ResponseEntity.ok(productService.getProductsSortedByPriceAsc());
		 
	 }
	 
	 @GetMapping("/price/orderby")
	 public ResponseEntity<List<Product>> getProductsSortedByDesc(@RequestParam String orderBy){
		 return ResponseEntity.ok(productService.getProductsSortedByPriceDesc(orderBy));
	 }
	 
	 @GetMapping("/byid/{id}")
	 public ResponseEntity<Product> getProductById(@PathVariable Long id){
		 return ResponseEntity.ok(productService.getProductById(id));
	 }
}
