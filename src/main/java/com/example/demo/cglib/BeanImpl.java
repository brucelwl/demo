package com.example.demo.cglib;

/**
 * Created by bruce on 2019/6/23 21:15
 */
public class BeanImpl extends Bean {

    @Override
    public void addSampleProperty(String sampleProperty) {
        this.sampleProperty = sampleProperty;
    }

}
