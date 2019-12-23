package com.example.demo.config;

import com.example.demo.ignoredependencyinterface.TestIgnore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Indexed;

/**
 * Created by bruce on 2019/8/1 17:36
 */
@Indexed
@Configuration
@EnableGitConfig(address = "http://localhost:454", branch = "dev")
public class AppConfig implements EnvironmentAware, InitializingBean {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private UserInfo userInfo;

    @Bean
    public TestIgnore testIgnore() {
        TestIgnore testIgnore = new TestIgnore();
        return testIgnore;
    }


    /*@Bean
     public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
            TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("param1", "value1");
            hashMap.put("param2", "value2");

            tomcatServletWebServerFactory.setInitParameters(hashMap);
            return tomcatServletWebServerFactory;
     }
    */

}
