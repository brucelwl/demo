package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by bruce on 2019/8/1 17:36
 */
@Configuration
@EnableGitConfig(address = "http://localhost:454", branch = "dev")
public class AppConfig {


}
