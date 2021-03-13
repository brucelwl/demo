package com.example.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.Priority;

/**
 * Created by bruce on 2021/3/12 14:51
 */
@Priority(8)
@Service
public class UserServiceImpl2 implements UserService {

    @Override
    public String saveHello() {
        return "这是UserServiceImpl 2";
    }
}
