package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class DemoApplicationTests {


    @Test
    public void contextLoads() {

        // Jedis jedis = new Jedis("127.0.0.1", 6379);
        // SetParams setParams = new SetParams();
        // //setParams.nx().ex(10);//key不存在时,设置key,并设置2s过期
        // setParams.nx().px(10000);//key不存在时,设置key,并设置2000ms过期
        //
        // String result = jedis.set("lock", "11", setParams);
        //
        // if ("OK".equals(result)) {
        //     System.out.println("获取锁成功:" + result);
        // } else {
        //     System.out.println("获取锁失败:" + result);
        // }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("key", "aa");

        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 10, TimeUnit.MILLISECONDS, runnables);

        //提前创建线程
        threadPoolExecutor.prestartCoreThread();
        //提前创建所有得线程
        threadPoolExecutor.prestartAllCoreThreads();


        threadPoolExecutor.setCorePoolSize(10);
        threadPoolExecutor.setMaximumPoolSize(20);




    }


}
