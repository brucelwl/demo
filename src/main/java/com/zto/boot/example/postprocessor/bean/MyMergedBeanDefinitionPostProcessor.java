package com.zto.boot.example.postprocessor.bean;

import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/7/5 10:34
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "custom.bean.post.processor.enable", havingValue = "true")
public class MyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MyMergedBeanDefinitionPostProcessor postProcessMergedBeanDefinition");
        }

    }

    @Override
    public void resetBeanDefinition(String beanName) {

        log.info("MyMergedBeanDefinitionPostProcessor resetBeanDefinition");

    }
}
