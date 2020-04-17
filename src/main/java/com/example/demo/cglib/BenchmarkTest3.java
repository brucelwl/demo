package com.example.demo.cglib;

import com.example.demo.config.ConfigA;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2019/6/23 21:26
 */

@BenchmarkMode(Mode.AverageTime) //测试的模式,可以测试吞吐,延时等;
@Warmup(iterations = 1)  //预热的循环次数
//@Threads(15)  //开启多少条线程测试
@State(Scope.Benchmark) //@State(Scope.Benchmark) 一个测试启用实例, @State(Scope.Thread) 每个线程启动一个实例
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 2, batchSize = 1024)
@OutputTimeUnit(TimeUnit.NANOSECONDS) //测试结果单位
public class BenchmarkTest3 {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkTest3.class);

    @Benchmark
    public void testObj1(Blackhole blackhole) throws Exception {

        long start = System.currentTimeMillis();
        BeanI beanI = ProxyInvoke.create();
        //System.out.println("jdk代理创建耗时:" + (System.currentTimeMillis() - start) + " " + beanI);

        beanI.addSampleProperty("testObj1");
        String sampleProperty = beanI.getSampleProperty();
        //blackhole.consume(sampleProperty);
    }

    @Benchmark
    public void testObj2(Blackhole blackhole) throws Exception {
        long start = System.currentTimeMillis();
        Bean proxyBean = Beans.newInstance(Bean.class);
        //System.out.println("cglib代理创建耗时:" + (System.currentTimeMillis() - start) + " " + proxyBean);

        proxyBean.addSampleProperty("testObj2");
        String sampleProperty = proxyBean.getSampleProperty();
        //blackhole.consume(sampleProperty);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(BenchmarkTest3.class.getName())
                //.output("benchmark/jedis-Throughput.log")
                .forks(0)
                .build();
        new Runner(options).run();

        //System.out.println("aaaa");
        //
        //for (int i = 0; i < 10; i++) {
        //    BenchmarkTest3 benchmarkTest3 = new BenchmarkTest3();
        //    benchmarkTest3.testObj1(null);
        //}
        //
        //for (int i = 0; i < 10; i++) {
        //    BenchmarkTest3 benchmarkTest3 = new BenchmarkTest3();
        //    benchmarkTest3.testObj2(null);
        //}


    }

}
