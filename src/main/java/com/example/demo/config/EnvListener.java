package com.example.demo.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by bruce on 2019/8/22 18:46
 */
public class EnvListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        AdapterConfigManager.loadAdapterPropertySource(event.getEnvironment());

        System.out.println();
    }
}
