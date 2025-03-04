//package com.gateway.poc.filter;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.gateway.poc.util.JwtService;
//
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements GatewayFilter{
//	
//	@Autowired
//	private JwtService jwtService;
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		 ServerHttpRequest request = exchange.getRequest();
//
//		 System.out.println("Bhanu ..........");
//	        final List<String> apiEndpoints = List.of("/auth/login", "/auth/**", "/eureka");
//
//	        
//	        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
//	                .noneMatch(uri -> r.getURI().getPath().contains(uri));
//
//	        if (isApiSecured.test(request)) {
//	            if (authMissing(request)) return onError(exchange);
//
//	            String token = request.getHeaders().getOrEmpty("Authorization").get(0);
//
//	            if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
//
//	            try {
//	            	jwtService.validateToken(token);
//	            } catch (Exception e) {
//	                return onError(exchange);
//	            }
//	        }
//	        return chain.filter(exchange);
//	}
//	private Mono<Void> onError(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//
//    private boolean authMissing(ServerHttpRequest request) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//
//}
