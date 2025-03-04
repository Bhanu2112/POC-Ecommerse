package com.cart.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.api.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	 Optional<Cart> findByUserId(Long userId);
}
