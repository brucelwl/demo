package com.example.demo.cglib;

/**
 * Created by bruce on 2019/6/23 20:21
 */
public abstract class Bean {

    String sampleProperty;

    public String getSampleProperty() {
        return sampleProperty;
    }

    public abstract void addSampleProperty(String sampleProperty);

    @Override
    public String toString() {
        return "sampleProperty:" + sampleProperty;
    }
}
