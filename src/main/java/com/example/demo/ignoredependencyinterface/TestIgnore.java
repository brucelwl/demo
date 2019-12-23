package com.example.demo.ignoredependencyinterface;

import com.example.demo.config.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * Created by bruce on 2019/12/2 20:32
 */
public class TestIgnore implements IgnoreUserInfoInject, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(TestIgnore.class);

    private UserInfo userInfo;

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;

        logger.info("userInfo 被注入");
    }

    @Override
    public void setEnvironment(Environment environment) {
        logger.info("Environment 被注入");
    }
}
