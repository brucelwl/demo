package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by bruce on 2019/7/1 10:37
 */
@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostConstruct
    public void init() {

        System.out.println(userService);


    }


}
