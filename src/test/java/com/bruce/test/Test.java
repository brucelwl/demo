package com.bruce.test;

/**
 * Created by bruce on 2021/1/11 9:47
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        Parent parent = new Parent();
        Parent.Inner inner = parent.newInner();

        parent.setAnInt(256);
        System.out.println(inner.getAnInt());
        System.out.println(parent.getAnInt());

        Thread.sleep(5000);

        inner.set(789);
        System.out.println(inner.getAnInt());
        System.out.println(parent.getAnInt());

    }
}
