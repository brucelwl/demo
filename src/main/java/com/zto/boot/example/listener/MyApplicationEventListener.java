package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.time.LocalDateTime;

/**
 * Created by bruce on 2019/7/2 10:36
 */
//@Configuration
public class MyApplicationEventListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener.class);


    public MyApplicationEventListener() {
        logPrint("MyApplicationEventListener create...");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ApplicationContextEvent) {
            //ContextRefreshedEvent
            //ContextClosedEvent 应用关闭时执行

            logPrint(event.getClass().getName());

        } else {
            logPrint(">>>>>>>>>> " + event.getClass().getName());

        }
    }

    private void logPrint(String mesg) {
        System.out.println(LocalDateTime.now().toString()
                + String.format("%-29s", "  sout") + Thread.currentThread().getName() + "} "
                + String.format("%-40s", MyApplicationEventListener.class.getSimpleName()) + " : " + mesg);
    }


}
