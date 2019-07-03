package com.example.demo.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by bruce on 2019/7/1 10:23
 */
@Configuration
public class MyConfig implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {

        String password = environment.getProperty("user.password");

        System.out.println("my password value :" + password);

    }
}
