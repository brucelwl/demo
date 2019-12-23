package com.example.demo;

import java.lang.management.ManagementFactory;

/**
 * Created by bruce on 2019/12/19 13:29
 */
public class HelloServiceImpl {

    public void sayHello() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("hello:" + name);
    }


}
