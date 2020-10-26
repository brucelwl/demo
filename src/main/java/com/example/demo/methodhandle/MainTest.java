package com.example.demo.methodhandle;

import java.lang.reflect.Proxy;

public class MainTest {

    public static void main(String[] args) {

        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserServiceInvoke.class.getClassLoader(),
                new Class[]{UserMapper.class}, new MyInvocationHandler());

        String address = userMapper.getAddress("12345");
        System.out.println(address);

        System.out.println();

        String userName = userMapper.getUserName("aa", true, 18);
        System.out.println(userName);
    }

}
