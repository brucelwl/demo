package com.zto.boot.example.postprocessor.bean;

import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/7/5 10:25
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "custom.bean.post.processor.enable", havingValue = "true")
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MyDestructionAwareBeanPostProcessor postProcessBeforeDestruction");
        }
    }


    @Override
    public boolean requiresDestruction(Object bean) {
        if (AopProxyUtils.ultimateTargetClass(bean) == UserService3.class) {
            log.info("MyDestructionAwareBeanPostProcessor requiresDestruction");
            return true;
        }
        return false;
    }
}
