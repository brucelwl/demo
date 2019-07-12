package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Created by bruce on 2019/7/1 10:23
 */
@Configuration
public class MyConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyConfig.class);

    @EventListener
    public void setEnvironment(ApplicationEvent event) {
        logger.info(event.getClass().getName());
    }
}
