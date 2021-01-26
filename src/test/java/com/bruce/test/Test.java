package com.bruce.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by bruce on 2021/1/11 9:47
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        // LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        // queue.offer("aaa");
        // queue.offer("bbb");
        // queue.offer("ccc");
        //
        // for (String s : queue) {
        //     System.out.println(queue.poll());
        // }


        StampedLock stampedLock = new StampedLock();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(stampedLock.tryOptimisticRead());
                }
            }).start();
        }


        stampedLock.tryWriteLock();


    }
}
