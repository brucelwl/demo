package com.example.demo.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/6/20 16:47
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Bean1 {

    public Bean1(){
        System.out.println("Bean1 create");
    }



}
