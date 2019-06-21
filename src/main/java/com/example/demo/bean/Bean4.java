package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/6/21 14:45
 */
@Component
public interface Bean4 {

    @Lookup
    Bean1 getBean1();

    @Lookup
    Bean2 getBean2();


}
