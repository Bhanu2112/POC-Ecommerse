package com.register.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class UserServiceAOP {


    private final Logger logger = LoggerFactory.getLogger(UserServiceAOP.class);

    @Pointcut("execution(* com.register.service.service.*.*(..))")
    public void userServiceMethods(){};

    @Around("userServiceMethods()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("[UserService] Entering method: {} | Args: {}" ,methodName, java.util.Arrays.toString(methodArgs));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("[UserService] Exception in method: {} | Error: {}", methodName,e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis();
        logger.info("[UserService] Exiting method: {} | Execution Time: {}ms | Response: {}", methodName, (endTime - startTime), result);

        
    }



}
