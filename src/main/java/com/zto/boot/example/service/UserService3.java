package com.zto.boot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by bruce on 2019/7/1 10:35
 */
@Slf4j
//@Service
public class UserService3 implements EnvironmentAware, ApplicationContextAware, InitializingBean, DisposableBean, SmartInitializingSingleton {

    public static final String BEAN_NAME = "userService3";

    private Environment environment;
    private ApplicationContext applicationContext;

    @Value("${user.name}")
    private String username;

    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String findUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    //TODO invoke InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation(Class<?> beanClass, String beanName)

    //AbstractAutowireCapableBeanFactory.doCreateBean() 555行创建对象

    public UserService3() {
        log.info("UserService3 create 0");
    }

    public UserService3(UserService userService, UserService2 userService2) {
        log.info("UserService3 create arg: {},{}", UserService.class.getSimpleName(), UserService2.class.getSimpleName());
    }

    public UserService3(UserService2 userService2, UserService userService) {
        log.info("UserService3 create arg: {},{}", UserService2.class.getSimpleName(), UserService.class.getSimpleName());
    }

    //TODO invoke MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName)

    //TODO invoke InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation
    //TODO invoke InstantiationAwareBeanPostProcessor.postProcessProperties

    //TODO invoke BeanNameAware,BeanClassLoaderAware,BeanFactoryAware

    //TODO invoke BeanPostProcessor.postProcessBeforeInitialization
    // EnvironmentAware,EmbeddedValueResolverAware,
    // ResourceLoaderAware,ApplicationEventPublisherAware,
    // MessageSourceAware,ApplicationContextAware
    //由ApplicationContextAwareProcessor.postProcessBeforeInitialization 回调实现
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        log.info("Aware setEnvironment");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        //Environment environment = applicationContext.getEnvironment();
        log.info("Aware setApplicationContext");
    }

    // @PostConstruct 由CommonAnnotationBeanPostProcessor
    // 继承 InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization实现
    // AbstractAutowireCapableBeanFactory.registerDisposableBeanIfNecessary(beanName, bean, mbd);
    // 适配器模式注册DisposableBean
    @PostConstruct
    private void init() {
        log.info("PostConstruct");
    }

    //AbstractAutowireCapableBeanFactory.invokeInitMethods
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }

    //AbstractAutowireCapableBeanFactory.invokeInitMethods
    public void customInitMethod() {
        log.info("customInitMethod");
    }

    //TODO invoke BeanPostProcessor.postProcessAfterInitialization

    @Override
    public void afterSingletonsInstantiated() {
        log.info("afterSingletonsInstantiated");
    }

    // @PreDestroy 由CommonAnnotationBeanPostProcessor
    // 继承 InitDestroyAnnotationBeanPostProcessor.postProcessBeforeDestruction
    @PreDestroy
    public void preDestroy() throws Exception {
        log.info("preDestroy");
    }

    //AbstractBeanFactory.registerDisposableBeanIfNecessary 1717行
    @Override
    public void destroy() throws Exception {
        log.info("destroy");
    }

    //DisposableBeanAdapter 239
    public void customDestroyMethod() {
        log.info("customDestroyMethod");
    }


}
