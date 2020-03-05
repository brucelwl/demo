package com.zto.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ConfigurationWarningsApplicationContextInitializer
 * 用于验证设置的包名是否包含org.springframework和org,如果包含则输出警告日志
 * 不过只是验证了@ComponentScan注解(包含继承的注解例如@SpringBootApplication)
 * 但是没有验证@ComponentScans注解
 */

@Slf4j
@SpringBootApplication
//@ComponentScan(basePackages = {"com.zto.boot.example","org.springframework"} )
//@ComponentScans({
//        @ComponentScan(basePackages = "org.springframework")
//
//})
public class DemoApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        //List<String> beanNames = Arrays.asList(context.getBeanDefinitionNames());

        //beanNames.stream().filter(beanName -> beanName.contains("."))
        //        .map(beanName -> String.format("%-50s", "") + context.getBean(beanName).getClass().getName())
        //        .forEach(System.out::println);
        //
        //beanNames.stream().filter(beanName -> !beanName.contains("."))
        //        .map(beanName -> String.format("%-50s", beanName) + context.getBean(beanName).getClass().getName())
        //        .forEach(System.out::println);

        synchronized (DemoApplication.class) {
            try {
                DemoApplication.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
