//package com.gateway.poc.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.gateway.poc.filter.JwtAuthenticationFilter;
//
//@Configuration
//public class RouterConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);
//
//    @Autowired
//    private JwtAuthenticationFilter authenticationFilter;
//
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        logger.info("Configuring routes...");
//
//        return builder.routes()
//                .route("product-service", r -> r.path("/product/**")
//                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://product-service"))
//                
//                .route("auth-service", r -> r.path("/auth/**")
//                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://auth-service"))
//                
//                .route("cart-service", r -> r.path("/cart/**")
//                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://cart-service"))
//                
//                .route("register-service", r -> r.path("/user/**")
//                        .filters(f -> f.filter(authenticationFilter))
//                        .uri("lb://register-service"))
//                
//                .build();
//    }
//}
