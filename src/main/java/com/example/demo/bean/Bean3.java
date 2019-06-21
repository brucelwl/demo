package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/6/20 16:47
 */
@Component
public abstract class Bean3 {

    public Bean3() {
        System.out.println("Bean3 create");
    }

    @Lookup
    public abstract Bean1 getBean1();

    @Lookup
    public Bean2 getBean2() {
        return null;
    }


}
