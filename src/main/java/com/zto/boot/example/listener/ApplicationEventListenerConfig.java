package com.zto.boot.example.listener;

import com.zto.boot.example.service.UserService3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * org.springframework.context.annotation.ConfigurationClassEnhancer
 * 验证@Configuration和@Component之间的区别
 *
 * <p>
 * <pre>
 * 使用@EventListener标记方法,创建监听器
 * 使用适配器模式,org.springframework.context.event.ApplicationListenerMethodAdapter
 * 支持方法名重载,原因这不是一个Spring Bean,
 * 实现原理见: org.springframework.context.event.EventListenerMethodProcessor
 * 只能监听org.springframework.context.event.ContextRefreshedEvent 以及之后的事件
 * </pre>
 * Created by bruce on 2019/7/4 10:36
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "enable.custom.eventListener", havingValue = "true")
public class ApplicationEventListenerConfig {

    //IllegalStateException Event parameter is mandatory for event listener method:
    //@EventListener
    //public void myAppMethodEventListener1() {
    //    log.info("myApplicationEventListener1");
    //}

    @Autowired
    public UserService3 userService3;

    @EventListener
    public void myAppMethodEventListener1(ApplicationEvent event) {
        log.info(event.getClass().getName());
    }

    //指定具体监听的事件类型
    @EventListener
    public void myAppMethodEventListener1(ApplicationStartedEvent event) {
        log.info("-------------" + event.getClass().getName());
    }

    //SpringBoot 启动流程最后一个事件
    @EventListener
    public void myAppMethodEventListener1(ApplicationReadyEvent event) {
        log.info("+++++++++++++" + event.getClass().getName());
    }

    @Bean
    public MyCustomEventListener myCustomEventListener() {
        return new MyCustomEventListener();
    }

    //验证@Configuration和@Component之间的区别
    //@Bean
    //public ConfigA newConfigA() {
    //    log.info("newConfigA 被调用");
    //    return new ConfigA();
    //}
    //
    //@Bean
    //public ConfigA configAaaa() {
    //    return newConfigA();
    //}
    //
    //@Bean
    //public ConfigA configAaa() {
    //    return newConfigA();
    //}


}
