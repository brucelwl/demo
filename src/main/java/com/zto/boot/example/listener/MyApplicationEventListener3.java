package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  通过@Component,@Configuration 注解添加监听器, 支持条件验证, 注入为Spring Bean
 *  只能监听org.springframework.context.event.ContextRefreshedEvent 以及之后的事件
 *
 * </pre>
 * <pre>
 *     在AbstractApplicationContext的registerListeners()方法中通过
 *     getBeanNamesForType(ApplicationListener.class, true, false)获取所有扫描到的监听器名称
 *     调用事件广播器的addApplicationListenerBean(listenerBeanName)方法,将事件监听器Bean名称添加到时间广播器中
 *
 *     org.springframework.context.support.ApplicationListenerDetector 实现了如下两个接口
 *     org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor,
 *     org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor
 *     这两个接口的都继承自同一个父接口
 *     org.springframework.beans.factory.config.BeanPostProcessor
 *     每当一个Bean执行完初始化方法后(例如InitializingBean接口的afterPropertiesSet和自定义的init-method已被执行),
 *     就会回调BeanPostProcessor的postProcessAfterInitialization(Object bean, String beanName),
 *     ApplicationListenerDetector 实现的postProcessAfterInitialization(Object bean, String beanName)
 *     会通过if(bean instanceof ApplicationListener) 检测bean是否监听器实现类,
 *     如果是则通过applicationContext.addApplicationListener(bean)将监听器
 *     添加到事件广播器和AbstractApplicationContext成员变量Set<ApplicationListener> applicationListeners中,
 *
 *     而ApplicationListenerDetector实例的创建则是在
 *     AbstractApplicationContext.prepareBeanFactory(ConfigurableListableBeanFactory beanFactory)方法中.
 *
 *     当事件广播器发布事件时,会调用其内部retrieveApplicationListeners(...)方法获取所有支持当前事件的监听器
 *     首先遍历已经保存的applicationListeners集合查询支持当前事件的监听器保存到集合;
 *     再通过保存的监听器beanName获取其Class,然后判断该Class是否支持当前事件,如果支持再通过beanName获取bean对象添加到集合中;
 *     最后排序,加入缓存,遍历广播事件
 *
 * </pre>
 * Created by bruce on 2019/7/2 10:36
 */
@Component
@ConditionalOnProperty(name = "enable.custom.eventListener", havingValue = "true")
public class MyApplicationEventListener3 implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener3.class);

    public MyApplicationEventListener3() {
        logger.info(" create...");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info(event.getClass().getName());
    }


}
