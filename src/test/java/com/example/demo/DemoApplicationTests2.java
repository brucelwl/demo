package com.example.demo;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

public class DemoApplicationTests2 {

    @Test
    public void contextLoads() {
        Integer integer = Integer.valueOf(15256);

        //自动排序
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        concurrentSkipListMap.put("ddd", "ddd");
        concurrentSkipListMap.put("bbb", "bbb");
        concurrentSkipListMap.put("ccc", "ccc");
        concurrentSkipListMap.put("aaa", "aaa");

        System.out.println(concurrentSkipListMap);



    }


}
