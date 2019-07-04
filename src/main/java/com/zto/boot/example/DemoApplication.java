package com.zto.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    //public DemoApplication() {
    //    System.out.println("DemoApplication create");
    //}

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


    }

}
