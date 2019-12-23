package com.example.demo.processor.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/12/3 9:39
 */
@Component
public class PriorityOrderedBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

    private static final Logger logger = LoggerFactory.getLogger(PriorityOrderedBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("postProcessBeanFactory");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
