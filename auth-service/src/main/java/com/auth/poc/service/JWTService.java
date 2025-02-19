package com.auth.poc.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth.poc.config.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	  public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	  
	  public String generateToken(String username) {
		
		  
	        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
	    
	        System.out.println(userDetails);
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, userDetails);
	    }
	  
	   private String createToken(Map<String, Object> claims, UserDetails userDetails) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(userDetails.getUsername())
	                .setIssuer(userDetails.getAuthorities().iterator().next().getAuthority())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
	                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	    }
	   private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

}
