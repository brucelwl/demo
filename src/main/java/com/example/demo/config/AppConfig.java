package com.example.demo.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by bruce on 2019/8/1 17:36
 */
@Configuration
@EnableGitConfig(address = "http://localhost:454", branch = "dev")
public class AppConfig implements EnvironmentAware , InitializingBean {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
