package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by bruce on 2019/7/27 12:44
 */
@CacheConfig(cacheNames = "user_info")
@Service
public class UserInfoServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Cacheable
    public UserInfo getUserInfo(String idCard) {
        logger.info("select not use cache");

        UserInfo userInfo = new UserInfo();
        userInfo.setIdCard(idCard);

        return userInfo;
    }


}
