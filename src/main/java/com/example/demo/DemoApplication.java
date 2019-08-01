package com.example.demo;

import com.example.demo.config.EnableGitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableGitConfig(address = "http://localhost:454",branch = "dev")
@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
         SpringApplication.run(DemoApplication.class, args);


    }

}
