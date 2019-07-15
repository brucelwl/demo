package com.zto.boot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("fat")
public class DemoApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {

        String property = environment.getProperty("sharding.basePackage");

        System.out.println(property);

    }

}
