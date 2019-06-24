package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bruce on 2019/6/24 19:06
 */
@ConditionalOnMissingBean(ConfigA.class)
@Configuration
public class ConfigC {

    public ConfigC(){

        System.out.println("ConfigC create");

    }


}
