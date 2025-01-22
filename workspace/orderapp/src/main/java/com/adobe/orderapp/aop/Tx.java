package com.adobe.orderapp.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// something like @Transactional
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tx {
}
