package com.adobe.orderapp.aop;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Aspect
public class TransactionalAspect {
//    @PersistenceContext
//    EntityManager em;
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(Tx)")
    public Object doTransaction(ProceedingJoinPoint pjp) throws Throwable {
       Object ret = null;

       try {
           logger.info("Start Transaction");
//         em.getTransaction().begin();
           // beginTransaction();
            ret = pjp.proceed(); // invoke actual method
           // commit();
//          em.getTransaction().commit();
           logger.info("Commit");
       } catch (Exception ex) {
           // rollback();
//         em.getTransaction().rollback();
           logger.info("Rollback");
       }

       return  ret;
    }
}
