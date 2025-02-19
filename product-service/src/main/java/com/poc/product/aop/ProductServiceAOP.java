package com.poc.product.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductServiceAOP {


	private final Logger logger = LoggerFactory.getLogger(ProductServiceAOP.class);

	@Pointcut("execution(* com.poc.product.service.*.*(..))")
	public void productServiceMethods() {
	}

	
	@Around("productServiceMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String methodName = joinPoint.getSignature().getName();
		Object[] methodArgs = joinPoint.getArgs();

		logger.info("[ProductService] Entering method: {} | Args: {}", methodName, java.util.Arrays.toString(methodArgs));

		Object result;
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			logger.error("[ProductService] Exception in method: {} | Error: {}", methodName, e.getMessage());
			throw e;
		}

		long endTime = System.currentTimeMillis();
		logger.info("[ProductService] Exiting method: {} | Execution Time: {}ms | Response: {}", methodName, endTime - startTime, result);

		return result;
	}

	@Before("execution(* com.poc.product.service.*.*(..))")
	public void logBeforeGetCart(JoinPoint joinPoint) {

		logger.info("[Product Service] Request to fetch cart for user ID: {}", joinPoint.getArgs()[0]);
	}
}
