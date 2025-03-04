package com.poc.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import com.poc.product.entity.Product;
import com.poc.product.exception.ProductNotFound;
import com.poc.product.repository.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@CachePut(value="product",key="#result.id")
	   public Product addProduct(Product product) {
		   product.setDeleted(false);
	        return productRepository.save(product);
	   }
	   @CacheEvict(value="product",allEntries = true)
	   public void softDeleteProduct(Long productId) {
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));
	        product.setDeleted(true);
	        productRepository.save(product);
	    }
	   
	   @CachePut(value="product",key="#productId")
	   public Product updateProductPrice(Long productId, Double newPrice) {
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));
	        product.setPrice(newPrice);
	        return productRepository.save(product);
	    }
	   
	   @Cacheable(value = "product",key="#name",unless = "#result == null")
	   public Product getProductByName(String name) {
		   return productRepository.findByProductName(name)
				   .orElseThrow(() -> new ProductNotFound("Product with name "+name+" not found"));
	   }
	   
	   
	   @Cacheable(value = "product",key = "#category", unless = "#result == null ")
	   public List<Product> getProductsByCategory(String category){
		   return productRepository.findByCategory(category);
	   }
	   
	   @Cacheable(value = "product",unless = "#result == null ")
	   public List<Product> getProductsSortedByPriceAsc() {
	        return productRepository.findByPriceAsc();
	    }
	   @Cacheable(value = "product",unless = "#result == null ")
	    public List<Product> getProductsSortedByPriceDesc(String orderBy) {
	        return productRepository.findByPriceDesc(orderBy);
	    }
	   
	   @Cacheable(value="product",key="#id")
	   public Product getProductById(Long id) {
		   return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product Not Found"));
	   }
	   

}
