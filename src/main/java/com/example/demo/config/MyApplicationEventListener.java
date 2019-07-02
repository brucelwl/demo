package com.example.demo.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by bruce on 2019/7/2 10:36
 */
public class MyApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        System.out.println(event.getClass().getName());


        if (event instanceof ApplicationContextEvent){
            System.out.println(event.getClass().getName());
        }




    }
}
