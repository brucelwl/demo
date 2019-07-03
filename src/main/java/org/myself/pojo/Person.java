package org.myself.pojo;

import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;

/**
 * Created by bruce on 2019/7/3 0:01
 */
public class Person {

    public String id;
    private UserService userService;
    protected UserController userController;
    UserController test;

    private String say(UserController content) {
        return "";
    }

    protected String say2(UserService content) {
        return "";
    }

    protected final String say3(String content) {
        return content;
    }

    public String say4(String content) {
        return content;
    }


}
