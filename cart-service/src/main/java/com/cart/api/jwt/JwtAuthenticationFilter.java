package com.cart.api.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		 try {
	            String token = request.getHeader("Authorization");

	            System.out.println(token);
	            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
	                Claims claims = jwtUtil.getClaims(token.substring(7));

	                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(claims.getIssuer());
	                System.out.println(authority);
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                        claims.getSubject(), null, Collections.singleton(authority));
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	               System.out.println( authentication.isAuthenticated());
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }

	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        filterChain.doFilter(request, response);
		
	}
	
	
}
