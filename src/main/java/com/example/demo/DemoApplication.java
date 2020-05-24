package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.loader.ExecutableArchiveLauncher;
import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.loader.MainMethodRunner;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringBoot打成jar包后,真正的jar启动类是{@link JarLauncher},
 * 通过反射调用应用启动类的main(String[] args),
 * 实则如果SpringBoot定义应用启动类的启动方法也是可以的
 *
 * @see ExecutableArchiveLauncher#getMainClass()
 * @see MainMethodRunner#run()
 */
@ServletComponentScan
//@EnableGitConfig(address = "http://localhost:454ranch = "dev")
//@SpringBootApplication(exclude = {HttpMessageConvertersAutoConfiguration.class})
@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);


    }

}
