package com.example.demo.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class MyServletContainerInitializer implements javax.servlet.ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println(".......................MyServletContainerInitializer");
    }
}
