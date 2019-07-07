package com.zto.boot.example.postprocessor.bean;

import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/7/5 10:30
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "custom.bean.post.processor.enable", havingValue = "true")
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    //对象创建之前执行
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MyInstantiationAwareBeanPostProcessor postProcessBeforeInstantiation");
        }

        return null;
    }

    /**
     * 返回true表示该对象有属性需要被赋值,
     * 紧接着会调用InstantiationAwareBeanPostProcessor.postProcessProperties()
     * 如果多个实现类,只要有一个返回false,
     * 那么全部实现类不会调用postProcessProperties(),所以这个方法spring默认为true
     * 见方法AbstractAutowireCapableBeanFactory.populateBean(...)
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {
            log.info("MyInstantiationAwareBeanPostProcessor postProcessAfterInstantiation");
        }

        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equalsIgnoreCase(UserService3.BEAN_NAME)) {

            log.info("MyInstantiationAwareBeanPostProcessor postProcessProperties");

            MutablePropertyValues propertyValues = (MutablePropertyValues) pvs;

            //AutowiredAnnotationBeanPostProcessor 通过反射给字段赋值,在处理@Value和@Autowired注解时,
            //会排序PropertyValues中以有的属性,不再处理
            //这种方式赋值字段必须要有标准Getter/Setter
            propertyValues.add("username", "1121");
            propertyValues.add("password", "1121");
        }
        return pvs;
    }

    //PropertyDescriptor[] pds 需要标准的getter setter
    //该方法已经被废弃,其和postProcessProperties同时使用时返回值不能同时为null
    //AbstractAutowireCapableBeanFactory. 1408行循环中断,
    //从而导致org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor无法处理@Autowired和@Value注解
    //@Override
    //public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
    //    return null;
    //}
}
