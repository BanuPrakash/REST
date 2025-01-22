package com.adobe.orderapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
    public Object doProfile(ProceedingJoinPoint pjp) throws  Throwable {
        long start = new Date().getTime();
            Object ret = pjp.proceed(); // invoke actual method Product getProductById(1)
        long endTime = new Date().getTime();
        logger.info("Time : " + (endTime - start) + " ms");
        return  ret;
    }


    @AfterThrowing(value = "execution(* com.adobe.orderapp.service.*.*(..))", throwing = "ex")
    public void catchException(JoinPoint jp, Exception ex) throws Throwable{
        logger.info("Boom :-(" + ex.getMessage());
    }

}
