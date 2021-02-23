package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class DemoApplicationTests {

    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        SetParams setParams = new SetParams();
        //setParams.nx().ex(10);//key不存在时,设置key,并设置2s过期
        setParams.nx().px(10000);//key不存在时,设置key,并设置2000ms过期

        String result = jedis.set("lock", "11", setParams);

        if ("OK".equals(result)) {
            System.out.println("获取锁成功:" + result);
        } else {
            System.out.println("获取锁失败:" + result);
        }


        stringRedisTemplate.opsForValue().setIfAbsent("aa", "11",10000, TimeUnit.MILLISECONDS);


        int[] ages = {};

    }


}
