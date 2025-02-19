package com.register.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class UserControllerAOP {



    private final Logger logger = LoggerFactory.getLogger(UserControllerAOP.class);

    @Pointcut("execution(* com.register.service.controller.*.*(..))")
    public void userControllerMethods(){};

    @Around("userControllerMethods()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("[UserController] Entering method: {} | Args: {}" ,methodName, java.util.Arrays.toString(methodArgs));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("[UserController] Exception in method: {} | Error: {}", methodName,e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis();
        logger.info("[UserController] Exiting method: {} | Execution Time: {}ms | Response: {}", methodName, (endTime - startTime), result);


    }
}
