package com.cart.api.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CartControllerAOP {
	
	private final Logger logger = LoggerFactory.getLogger(CartControllerAOP.class);
	
	@Pointcut("execution(* com.cart.api.controller.CartController.*(..))")
    public void controllerMethods() {}

	
	
	@Around("controllerMethods()")
	public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		// Logging method entry
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		logger.info("[Cart Controller] Entering: {} | Args: {}", methodName, Arrays.toString(args));

		Object result;
		try {
			result = joinPoint.proceed(); // Proceed with the method execution
		} catch (Exception e) {
			logger.error("[Cart Controller] Exception in: {} | Error: {}", methodName, e.getMessage());
			throw e; // Re-throw the exception after logging
		}

		long endTime = System.currentTimeMillis();

		logger.info("[Cart Controller] Exiting: {} | Execution Time: {}ms | Response: {}", methodName,endTime-startTime,result);

		return result;
	}
	
	@AfterReturning(value = "controllerMethods()", returning = "result")
	public void logsAfterReturningDisplay(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		logger.info(" [Cart Controller] Successfully executed: {} | Returned: {}", methodName, result);
	}
}
