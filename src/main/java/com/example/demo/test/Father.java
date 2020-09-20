package com.example.demo.test;

public class Father extends GrandFather {

    @Override
    protected void thinking() {
        System.out.println("I 'm father!");
    }

    private void hello() {
        System.out.println("father say hello !!!");
    }

}
