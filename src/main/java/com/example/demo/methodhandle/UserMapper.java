package com.example.demo.methodhandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface UserMapper {

    public static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    String getAddress(String userId);

    default String getUserName(String userName, Boolean yes, int age) {
        logger.info("bruce");
        return "bruce " + userName + " " + yes + " " + age;
    }
}
