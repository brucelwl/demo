package com.example.demo.bean;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bruce on 2020/4/24 13:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnConditionalOfPrefix.class)
public @interface ConditionalOnPrefix {

    String prefix();

    /**
     * 默认是启用状态
     */
    boolean havingValue() default true;

}
