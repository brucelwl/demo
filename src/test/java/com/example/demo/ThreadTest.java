package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 三条线程, 按照顺寻, 每条打印10个数字
 */
public class ThreadTest {

    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        int threadCount = 3;
        int printCount = 10;
        for (int i = 2; i >= 0; i--) {
            new Thread(new PrintRunnable(i, threadCount, printCount)).start();
            System.out.println("创建线程" + i);
        }

        //new Thread(new PrintRunnable(2, threadCount, printCount)).start();
        //new Thread(new PrintRunnable(1, threadCount, printCount)).start();
        //
        //new Thread(new PrintRunnable(0, threadCount, printCount)).start();

    }

    public static class PrintRunnable implements Runnable {

        private int id;
        private int count;
        private int threadCount;

        public PrintRunnable(int id, int threadCount, int count) {
            this.id = id;
            this.count = count;
            this.threadCount = threadCount;
        }

        @Override
        public void run() {
            while (atomicInteger.get() <= count) {
                if (atomicInteger.get() % threadCount == id) {
                    for (int i = 10 * (atomicInteger.get() - 1) + 1; i <= 10 * atomicInteger.get(); i++) {
                        System.out.print(i + " ");
                    }
                    System.out.println("线程" + id + "执行完成");
                    atomicInteger.getAndAdd(1);

                    //TODO 这种方式有问题,如果notifyAll在wait之前执行,会导致无法唤醒线程
                    synchronized (PrintRunnable.class) {
                        PrintRunnable.class.notifyAll();
                    }
                } else {
                    //Thread.yield();
                    try {
                        Thread.sleep(1000);
                        synchronized (PrintRunnable.class) {
                            PrintRunnable.class.wait();
                            //System.out.println(id + " wait");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
