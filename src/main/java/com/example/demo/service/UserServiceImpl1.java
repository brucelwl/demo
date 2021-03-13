package com.example.demo.service;

import org.springframework.context.annotation.Primary;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;

/**
 * Created by bruce on 2021/3/12 14:51
 */
@Primary
@Priority(10)
@Service
public class UserServiceImpl1 extends OrderComparator implements UserService  {

    @Override
    public String saveHello() {
        return "这是UserServiceImpl 1";
    }

    @Override
    public Integer getPriority(Object obj) {
        return 1;
    }
}
