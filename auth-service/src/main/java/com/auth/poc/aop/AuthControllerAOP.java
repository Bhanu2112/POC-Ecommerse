package com.auth.poc.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthControllerAOP {

	

	@Pointcut("execution(* com.auth.poc.controller.*.*(..))")
    public void controllerMethods() {}

	
	
	@Around("controllerMethods()")
	public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		// Logging method entry
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		System.out.println("[Auth Controller] Entering: " + methodName + " | Args: " + Arrays.toString(args));

		Object result;
		try {
			result = joinPoint.proceed(); // Proceed with the method execution
		} catch (Exception e) {
			System.err.println("[Auth Controller] Exception in: " + methodName + " | Error: " + e.getMessage());
			throw e; // Re-throw the exception after logging
		}

		long endTime = System.currentTimeMillis();
		System.out.println("[Auth Controller] Exiting: " + methodName + " | Execution Time: " + (endTime - startTime)
				+ "ms | Response: " + result);

		return result;
	}
	
	@AfterReturning(value = "controllerMethods()", returning = "result")
	public void logsAfterReturningDisplay(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println(" [Auth Controller] Successfully executed: " + methodName + " | Returned: " + result);
	}
}
