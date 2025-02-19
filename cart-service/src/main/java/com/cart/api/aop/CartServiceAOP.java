package com.cart.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CartServiceAOP {


    private final Logger logger = LoggerFactory.getLogger(CartServiceAOP.class);

	    @Pointcut("execution(* com.cart.api.service.CartService.*(..))")
	    public void cartServiceMethods() {}

	    @Around("cartServiceMethods()")
	    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
	        long startTime = System.currentTimeMillis();
	        String methodName = joinPoint.getSignature().getName();
	        Object[] methodArgs = joinPoint.getArgs();
	        
	        logger.info("[CartService] Entering method: {} | Args: {}" ,methodName, java.util.Arrays.toString(methodArgs));

	        Object result;
	        try {
	            result = joinPoint.proceed();
	        } catch (Exception e) {
	            logger.error("[CartService] Exception in method: {} | Error: {}", methodName,e.getMessage());
	            throw e;
	        }

	        long endTime = System.currentTimeMillis();
	        logger.info("[CartService] Exiting method: {} | Execution Time: {}ms | Response: {}", methodName, (endTime - startTime), result);

	        return result;
	    }
	    
	    
	    @Before("execution(* com.cart.api.service.CartService.getCart(..))")
	    public void logBeforeGetCart(JoinPoint joinPoint) {
	        Long userId = (Long) joinPoint.getArgs()[0];
	        logger.info("[Cart Service] Request to fetch cart for user ID: {}", userId);
	    }

}
