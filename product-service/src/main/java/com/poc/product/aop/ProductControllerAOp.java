//package com.poc.product.aop;
//
//import java.util.Arrays;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class ProductControllerAOp {
//
//	private final Logger logger = LoggerFactory.getLogger(ProductControllerAOp.class);
//
//	@Pointcut("execution(* com.poc.product.controller.ProductController.*(..))")
//    public void controllerMethods() {}
//
//	
//	
//	@Around("controllerMethods()")
//	public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
//		long startTime = System.currentTimeMillis();
//
//		// Logging method entry
//		String methodName = joinPoint.getSignature().getName();
//		Object[] args = joinPoint.getArgs();
//		logger.info("[Product Controller] Entering: {} | Args: {}", methodName, Arrays.toString(args));
//		Object result;
//		try {
//			result = joinPoint.proceed();
//		} catch (Exception e) {
//			logger.error("[Product Controller] Exception in: {} | Error: {}", methodName, e.getMessage());
//			throw e;
//		}
//
//		long endTime = System.currentTimeMillis();
//		logger.info("[Product Controller] Exiting: {} | Execution Time: {}ms | Response: {}", methodName, (endTime - startTime), result);
//
//		return result;
//	}
//	
//	@AfterReturning(value = "controllerMethods()", returning = "result")
//	public void logsAfterReturningDisplay(JoinPoint joinPoint, Object result) {
//		String methodName = joinPoint.getSignature().getName();
//		logger.info("[Product Controller] Successfully executed: {} | Returned: {}", methodName, result);
//	}
//}
