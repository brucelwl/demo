package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by bruce on 2019/7/17 18:14
 */
@ConfigurationProperties("my.application")
@Setter
@Getter
public class ApplicationConfig {

    // application name
    private String name;

    // module version
    private String version;

    // architecture layer
    @Deprecated
    private String architecture = "hello";

}
