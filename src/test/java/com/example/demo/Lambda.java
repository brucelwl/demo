package com.example.demo;

import java.util.function.Consumer;

/**
 * Created by bruce on 2020/4/10 14:10
 */
public class Lambda {
    static {
        System.setProperty("jdk.internal.lambda.dumpProxyClasses", ".");
    }
    public static void main(String[] args) {
        Consumer<String> c = s -> System.out.println(s);
        c.accept("hello lambda");
    }
}
