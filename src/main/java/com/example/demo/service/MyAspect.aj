package com.example.demo.service;

public aspect MyAspect {

    before():execution(* com.example.demo.*.*(..)){

        System.out.println("在目标方法之前执行");

    }


    after():execution(* com.example.demo.*.*(..)){

        System.out.println("在目标方法之后执行");

    }



}