package com.example.demo.controller;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.FrameworkServlet;

@RestController
public class HelloController {

    /**
     * @see FrameworkServlet line 1137
     * @see DispatcherServletAutoConfiguration line 98
     * 
     * I want to be able to control whether or not to post this event.
     * If it is a large number of requests, but many listeners listen to this event
     * that does not need to be processed, it will cause performance problems.
     */
    @GetMapping("/hello")
    public String hello(){
        return "The ServletRequestHandledEvent was released, please see the console";
    }



}
