package com.example.demo.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/12/4 13:14
 */
@Component
public class MyLifecycle2 implements Lifecycle {

   private static final Logger logger = LoggerFactory.getLogger(MyLifecycle2.class);

    @Override
    public void start() {
        logger.info("start");
    }

    @Override
    public void stop() {
        logger.info("stop");
    }

    @Override
    public boolean isRunning() {

        logger.info("isRunning");

        return true;
    }
}
