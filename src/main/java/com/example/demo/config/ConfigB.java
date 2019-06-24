package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bruce on 2019/6/24 16:49
 */
@ConditionalOnBean(ConfigC.class)
@Configuration
public class ConfigB  {

    public ConfigB(){
        System.out.println("ConfigB create");
    }

}
