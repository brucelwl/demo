package com.example.demo;

import org.springframework.context.ApplicationEvent;

/**
 * Created by bruce on 2019/11/21 15:24
 */
public class EventA extends ApplicationEvent {


    private static final long serialVersionUID = -1489132823480352408L;

    public EventA(Object source) {
        super(source);
    }



}
