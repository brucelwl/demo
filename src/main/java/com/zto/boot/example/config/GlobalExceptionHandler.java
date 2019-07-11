package com.zto.boot.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by bruce on 2019/7/11 17:41
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler(){
        System.out.println("....GlobalExceptionHandler");
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public String exceptionHandler(Exception e) {

        logger.info("GlobalExceptionHandler Exception");

        return "........";
    }
}