package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by bruce on 2019/8/22 18:46
 */
public class EnvListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(EnvListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        AdapterConfigManager.loadAdapterPropertySource(event.getEnvironment());

        logger.info("EnvListener");

        logger.info("pid:{}", event.getEnvironment().getProperty("PID"));

    }
}
