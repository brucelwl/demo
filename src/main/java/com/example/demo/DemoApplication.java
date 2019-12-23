package com.example.demo;

import com.example.demo.config.Address;
import com.example.demo.config.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@ServletComponentScan
//@EnableGitConfig(address = "http://localhost:454",branch = "dev")
@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        UserInfo bean = context.getBean(UserInfo.class);

        System.out.println(bean);

        List<Address> addresses = bean.getAddresses();
        for (Address address : addresses) {
            System.out.println(address);
        }

        //TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        //tomcatServletWebServerFactory.setProtocol(Http11AprProtocol.class.getName());



    }

}
