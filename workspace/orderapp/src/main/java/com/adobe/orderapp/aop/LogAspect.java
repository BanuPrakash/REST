package com.adobe.orderapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // we want this code to be called before every method call of service
    @Before("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        logger.info("Called : " + jp.getSignature());
        Object[] args = jp.getArgs();
        for(Object arg : args) {
            logger.info("Argument :" + arg);
        }
    }

    @After("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        logger.info("************");
    }

    @Around("execution(* com.adobe.orderapp.service.*.*(..))")
    public Object doProfile(ProceedingJoinPoint jp) throws  Throwable {
        long start = new Date().getTime();
            Object ret = jp.proceed(); // invoke actual method
        long endTime = new Date().getTime();
        logger.info("Time : " + (endTime - start) + " ms");
        return  ret;
    }
}
