package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 通过 context.listener.classes=com.zto.boot.example.listener.MyApplicationEventListener2
 * 配置监听器,只能监听ApplicationEnvironmentPreparedEvent以及之后的事件类型.
 * <p>
 * 实现原理见: org.springframework.boot.context.config.DelegatingApplicationListener
 * <p>
 * Created by bruce on 2019/7/2 10:36
 */
public class MyApplicationEventListener2 implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener2.class);

    public MyApplicationEventListener2() {
        // logger.info(" create...");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //   logger.info(event.getClass().getName());
    }


}
