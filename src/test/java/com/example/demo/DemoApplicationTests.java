package com.example.demo;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.UserInfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    //com.example.demo.service.UserInfoServiceImpl' but was actually of type 'com.sun.proxy.$Proxy49'
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Test
    public void contextLoads() {
        UserInfo userInfo = userInfoService.getUserInfo("11222");
        System.out.println(userInfo);

        userInfo = userInfoService.getUserInfo("11222");
        System.out.println(userInfo);
    }

    @Test
    public void selectUserInfoTest() {
        UserInfo userInfo = userInfoService.selectUserInfo("11222");
        System.out.println(userInfo);

        userInfo = userInfoService.selectUserInfo("11222");
        System.out.println(userInfo);
    }





}
