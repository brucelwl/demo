package com.example.demo.processor.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/12/3 14:29
 */
@Component
public class OrderedBeanPostProcessor implements BeanPostProcessor, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(OrderedBeanPostProcessor.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        logger.info("postProcessBeforeInitialization");

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        logger.info("postProcessAfterInitialization");

        return bean;
    }
}
