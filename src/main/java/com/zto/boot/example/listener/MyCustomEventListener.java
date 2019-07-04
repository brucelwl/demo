package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.time.LocalDateTime;

public class MyCustomEventListener implements ApplicationListener<CustomApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener.class);

    @Override
    public void onApplicationEvent(CustomApplicationEvent event) {
        logger.info("这是自定义的事件类型...");
    }
}
