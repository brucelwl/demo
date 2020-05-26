package com.example.demo.controller;

import com.example.demo.config.UserInfo;
import com.example.demo.status.SystemInfo;
import com.example.demo.status.SystemInfoCollector;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by bruce on 2019/9/11 14:23
 */
@RestController
public class WebController {


    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // int a = 10 / 0;

        response.sendError(401, "this is error info");

    }

    @GetMapping("/user")
    public UserInfo userInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setNum(15.639);
        userInfo.setSalary(15112);
        userInfo.setBrithDay(new Date());
        return userInfo;
    }

    @GetMapping("/user2")
    public UserInfo userInfo2(String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNum(15.639);
        userInfo.setSalary(15112);
        userInfo.setUserName(name);
        userInfo.setBrithDay(new Date());
        return userInfo;
    }

    @GetMapping("/user3")
    public UserInfo userInfo3(UserInfo user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNum(user.getNum());
        userInfo.setSalary(user.getSalary());
        userInfo.setUserName(user.getUserName());
        userInfo.setBrithDay(new Date());
        return userInfo;
    }

    @GetMapping("/str")
    public String str(){
        return "返回中文字符串abc123";
    }


    @GetMapping("/collector")
    public SystemInfo SystemInfoCollector(){
        SystemInfoCollector systemInfoCollector = new SystemInfoCollector();
        SystemInfo systemInfo = systemInfoCollector.collect();

        return systemInfo;
    }

    //@RequestMapping("/error")
    //public void error(Exception e){
    //
    //    System.out.println(e.getMessage());
    //
    //}

    //@Override
    //public String getErrorPath() {
    //    return "/error";
    //}
}
