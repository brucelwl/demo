package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentHashMap;

public class DemoApplicationTests2 {

    @Test
    public void contextLoads() throws IllegalAccessException, InstantiationException {

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

        Object o = map.putIfAbsent("aaa", "aaa");

        Object aaa = map.computeIfAbsent("aaa", (key) -> "aaaa");

        Object aaa1 = map.get("aaa");

        System.out.println(o);
        System.out.println(aaa);


        //FooBar.FooBarConfig fooBarConfig = new FooBar.FooBarConfig();
        //System.out.println(fooBarConfig);

        FooBar.FooBarConfig fooBarConfig = FooBar.FooBarConfig.class.newInstance();


    }


}
