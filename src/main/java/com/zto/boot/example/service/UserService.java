package com.zto.boot.example.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by bruce on 2019/7/1 10:35
 */
@Service
public class UserService implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //Map<String, ApplicationListener> beansOfType = applicationContext.getBeansOfType(ApplicationListener.class);

        if (applicationContext instanceof AbstractApplicationContext){
            Collection<ApplicationListener<?>> applicationListeners = ((AbstractApplicationContext) applicationContext).getApplicationListeners();

            System.out.println();
        }

        System.out.println();

    }
}
