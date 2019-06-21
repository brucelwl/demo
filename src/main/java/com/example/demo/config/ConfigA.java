package com.example.demo.config;

import com.example.demo.bean.Bean3;
import com.example.demo.bean.Bean4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by bruce on 2019/6/20 16:44
 */
@Component
public class ConfigA {

    @Autowired
    private Bean3 bean3;

    @Autowired
    private Bean4 bean4;

    @PostConstruct
    public void init() {

        System.out.println(bean3);
        System.out.println(bean4);

        System.out.println(bean3.getBean1());
        System.out.println(bean3.getBean1());
        System.out.println(bean3.getBean2());
        System.out.println(bean3.getBean2());

        System.out.println(bean4.getBean1());
        System.out.println(bean4.getBean1());
        System.out.println(bean4.getBean2());
        System.out.println(bean4.getBean2());

    }


}
