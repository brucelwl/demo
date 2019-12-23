package com.example.demo.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by bruce on 2019/11/24 20:34
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();

        //servletContext.setInitParameter("appId", "123456");

        logger.info("...");

    }
}
