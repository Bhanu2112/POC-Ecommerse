package com.poc.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
Optional<Product> findByProductName(String name);
    
    List<Product> findByCategory(String category);
    
    @Query("SELECT p FROM Product p WHERE p.isDeleted = false ORDER BY p.price ASC")
    List<Product> findByPriceAsc();
    
    @Query("SELECT p FROM Product p WHERE p.isDeleted = false ORDER BY p.price DESC")
    List<Product> findByPriceDesc(@Param(value = "orderBy") String orderBy);

}
