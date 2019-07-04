package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <pre>
 * 在ApplicationContextInitializer中通过ConfigurableApplicationContext添加监听器
 * 只能监听org.springframework.context.event.ContextRefreshedEvent 以及之后的事件
 * </pre>
 * <p>
 * Created by bruce on 2019/7/2 10:36
 */
public class MyApplicationEventListener4 implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener4.class);

    public MyApplicationEventListener4() {
        //logger.info(" create...");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
       // logger.info(event.getClass().getName());
    }


}
