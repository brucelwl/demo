package com.zto.boot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by bruce on 2019/7/1 10:35
 */
@Slf4j
@Service
public class UserService implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //Map<String, ApplicationListener> beansOfType = applicationContext.getBeansOfType(ApplicationListener.class);

        if (applicationContext instanceof AbstractApplicationContext) {
            Collection<ApplicationListener<?>> applicationListeners = ((AbstractApplicationContext) applicationContext).getApplicationListeners();

            for (ApplicationListener<?> applicationListener : applicationListeners) {
                //log.info(applicationListener.getClass().getName());
            }
            //通过DefaultListableBeanFactory手动注册bean,这种方式不支持Aware回调接口
            //优先级高于注解标记的bean,但是名称不会保存在manualSingletonNames, 当注入同名的BeanDefinition时,
            //执行类路径扫描后,UserService2的BeanDefinition就被保存在DefaultListableBeanFactory的beanDefinitionMap中
            //但是这时候还没有调用getBean()实例化,而UserService由于按照名称排序优先于UserService2实例化,
            //当回调ApplicationContextAware接口方法时手动调用DefaultListableBeanFactory注入bean实例,其会调用
            //父类DefaultSingletonBeanRegistry的registerSingleton(...),
            //将对象添加到父类DefaultSingletonBeanRegistry的singletonObjects中,完成bean实例的注册
            //然后DefaultListableBeanFactory判断beanDefinitionMap是否有同名的BeanDefinition,
            //有的话则不在manualSingletonNames添加bean名称,没有则添加,此时因为有所以"userService2"不会被添加到manualSingletonNames
            //当调用DefaultListableBeanFactory的preInstantiateSingletons()方法时会调用getBean(...)完成所有非懒加载的Bean的实例化
            //会先判断父类DefaultSingletonBeanRegistry中是否有这个Bean对象,当发现有userService2这个bean,导致注解的UserService2不会被实例化

            //UserService2 singletonObject = new UserService2();
            //ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) applicationContext;
            //appContext.getBeanFactory().registerSingleton("userService2", singletonObject);

            //Object userService2 = appContext.getBeanFactory().getBean("userService2");
            //
            //UserService2 bean = applicationContext.getBean(UserService2.class);



            //BeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
            //BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            //((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(, );

        }


    }
}
