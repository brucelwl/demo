package com.example.demo;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

/**
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest {

    @Test
    public void test() throws InterruptedException, BrokenBarrierException {

        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获取到资源!");

                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName() + "释放资源!");
                    semaphore.release();
                }
            }).start();
        }

        Thread.sleep(30000);
    }


}
