package com.zto.boot.example.postprocessor.bean;

import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/7/5 10:11
 */
@Slf4j
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnProperty(value = "custom.bean.post.processor.enable", havingValue = "true")
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {

            log.info("MyBeanPostProcessor postProcessBeforeInitialization");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {

            log.info("MyBeanPostProcessor postProcessAfterInitialization");

        }

        return bean;
    }
}
