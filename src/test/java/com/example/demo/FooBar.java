package com.example.demo;

import java.util.logging.Logger;

/**
 * Created by bruce on 2019/11/25 10:44
 */
public class FooBar {
    public FooBar() {
        Logger.getGlobal().info("Starting FooBar...");
    }

    class FooBarConfig {

        String bar;

        public void setBar(String bar) {
            this.bar = bar;
        }

    }
}
