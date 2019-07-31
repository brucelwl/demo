package com.example.demo;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        UserInfoServiceImpl userInfoService = context.getBean(UserInfoServiceImpl.class);

        UserInfo userInfo = userInfoService.getUserInfo("11222");
        logger.info(userInfo.toString());

        userInfo = userInfoService.getUserInfo("11222");
        logger.info(userInfo.toString());

    }

}
