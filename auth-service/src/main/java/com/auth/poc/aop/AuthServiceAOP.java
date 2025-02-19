package com.auth.poc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthServiceAOP {

	
	
	 @Pointcut("execution(* com.auth.poc.service.*.*(..))")
	    public void AuthServiceMethods() {}

	    @Around("AuthServiceMethods()")
	    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
	        long startTime = System.currentTimeMillis();
	        String methodName = joinPoint.getSignature().getName();
	        Object[] methodArgs = joinPoint.getArgs();
	        
	        System.out.println("[Auth Service] Entering method: " + methodName + " | Args: " + java.util.Arrays.toString(methodArgs));

	        Object result;
	        try {
	            result = joinPoint.proceed();
	        } catch (Exception e) {
	            System.err.println("[Auth Service] Exception in method: " + methodName + " | Error: " + e.getMessage());
	            throw e;
	        }

	        long endTime = System.currentTimeMillis();
	        System.out.println("[Auth Service] Exiting method: " + methodName + " | Execution Time: " + (endTime - startTime) + "ms | Response: " + result);

	        return result;
	    }
	    
	    
	    @Before("execution(* com.poc.Auth .service.*.*(..))")
	    public void logBeforeGetCart(JoinPoint joinPoint) {
	      
	        System.out.println("[Auth  Service] Request to fetch cart for user ID: " +  joinPoint.getArgs()[0]);
	    }
}
