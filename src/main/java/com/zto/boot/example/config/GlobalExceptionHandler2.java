package com.zto.boot.example.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by bruce on 2019/7/11 17:41
 */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 50)
@RestControllerAdvice
public class GlobalExceptionHandler2 {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler2.class);

    public GlobalExceptionHandler2() {
        System.out.println("....GlobalExceptionHandler2");
    }

    @ExceptionHandler({Exception.class})
    public String exceptionHandler(Exception e) {
        logger.info("exceptionHandler2...Exception");
        return "........";
    }

    @ExceptionHandler({ArithmeticException.class})
    public String exceptionHandler2() {

        logger.info("exceptionHandler2...ArithmeticException");

        return "exceptionHandler2";
    }


}