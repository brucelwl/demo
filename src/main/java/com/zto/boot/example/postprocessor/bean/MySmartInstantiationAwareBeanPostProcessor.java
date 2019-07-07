package com.zto.boot.example.postprocessor.bean;

import com.zto.boot.example.service.UserService;
import com.zto.boot.example.service.UserService2;
import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * Created by bruce on 2019/7/5 10:31
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "custom.bean.post.processor.enable", havingValue = "true")
public class MySmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    //预测最终从InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation返回的bean的实际类型,
    //spring中的默认实现就是null,见 AbstractAutowireCapableBeanFactory 646
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {

        //if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
        //    log.info("MySmartInstantiationAwareBeanPostProcessor predictBeanType");
        //}

        return null;
    }

    /**
     * 在反射创建对象之前,通过该接口可以指定当前class选用哪一个构造函数创建对象
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MySmartInstantiationAwareBeanPostProcessor determineCandidateConstructors");

            //try {
            //    //如果返回多个,则按照参数个数排序,返回null,则按照Spring规则排序
            //    Constructor<?> constructor = beanClass.getConstructor(UserService.class, UserService2.class);
            //    return new Constructor[]{constructor};
            //} catch (Exception e) {
            //    e.printStackTrace();
            //}
        }

        return null;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MySmartInstantiationAwareBeanPostProcessor getEarlyBeanReference");
        }

        return bean;
    }
}
