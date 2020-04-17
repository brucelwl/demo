package com.example.demo;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2020/4/17 22:34
 */
public class MeterExample {
    private static MetricRegistry metricRegistry = new MetricRegistry();
    private static Meter tqsMeter = metricRegistry.meter("tqs");
    private static Meter sizeMeter = metricRegistry.meter("volume");

    public static void main(String[] args) {

        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.MINUTES)
                .build();

        reporter.start(5, TimeUnit.SECONDS);

        for (int i = 0; i < 13; i++) {
            handleReq(new byte[ThreadLocalRandom.current().nextInt(1000)]);
        }

    }

    private static void handleReq(byte[] req) {

        tqsMeter.mark();
        sizeMeter.mark(req.length);

        randomSleep();

    }


    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
