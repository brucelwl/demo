package com.example.demo;

import org.junit.Test;
import org.springframework.boot.env.RandomValuePropertySource;

/**
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest {

    @Test
    public void test() {
        RandomValuePropertySource propertySource = new RandomValuePropertySource("random");

        System.out.println(propertySource.getProperty("random.int"));
        System.out.println(propertySource.getProperty("random.long"));
        System.out.println(propertySource.getProperty("random.uuid"));

        System.out.println(propertySource.getProperty("random.int[0,10)"));
        System.out.println(propertySource.getProperty("random.long[0,1000)"));

        //返回随机md5值,random.后面随便写
        System.out.println(propertySource.getProperty("random.other"));


        

    }


}
