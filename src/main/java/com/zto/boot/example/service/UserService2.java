package com.zto.boot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by bruce on 2019/7/1 10:35
 */
@Slf4j
//@ManagedBean
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserService2 implements ApplicationContextAware {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //Map<String, ApplicationListener> beansOfType = applicationContext.getBeansOfType(ApplicationListener.class);

        if (applicationContext instanceof AbstractApplicationContext) {
            Collection<ApplicationListener<?>> applicationListeners = ((AbstractApplicationContext) applicationContext).getApplicationListeners();

            for (ApplicationListener<?> applicationListener : applicationListeners) {
                //log.info(applicationListener.getClass().getName());
            }
        }
    }


    public String getUserName(String id) {

        return "name_" + id;

    }


}
