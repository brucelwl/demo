package com.zto.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bruce on 2019/7/3 15:50
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnvironmentTest {

    @Autowired
    private ConfigurableEnvironment environment;

    /**
     * <pre>
     *   测试ConfigurableEnvironment中的ConfigurationPropertySourcesPropertySource 导致冗余的遍历
     *   相关code:
     *   org.springframework.boot.SpringApplication.prepareEnvironment(...)
     *
     *   ConfigurationPropertySources.attach(environment);
     *
     *   org.springframework.core.env.PropertySourcesPropertyResolver.getProperty(...) for 遍历
     *
     * </pre>
     */
    @Test
    public void test() {
        String property = environment.getProperty("user.password");
        log.info(property);
    }


}
