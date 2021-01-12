package com.bruce.test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2021/1/11 9:42
 */
public class Parent {

    private Integer anInt;

    public Parent() {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " " + anInt);
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    public Integer getAnInt() {
        return anInt;
    }

    public void setAnInt(Integer anInt) {
        this.anInt = anInt;
    }

    public Inner newInner() {
        return new Inner();
    }

    public class Inner {
        //private Integer anInt;

        public void set(Integer value) {
            anInt = value;
        }

        public Integer getAnInt() {
            return anInt;
        }
    }


}
