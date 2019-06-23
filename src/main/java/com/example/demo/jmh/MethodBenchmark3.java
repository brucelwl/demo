package com.example.demo.jmh;

import com.example.demo.bean.Bean1;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
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
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2019/6/21 17:40
 */
//@Fork(value = 1, jvmArgsPrepend = "-Xmx256m")
@BenchmarkMode(Mode.AverageTime) //测试的模式,可以测试吞吐,延时等;
@Warmup(iterations = 1)  //预热的循环次数
//@Threads(15)  //开启多少条线程测试
@State(Scope.Benchmark) //@State(Scope.Benchmark) 一个测试启用实例, @State(Scope.Thread) 每个线程启动一个实例
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 2, batchSize = 1024)
@OutputTimeUnit(TimeUnit.NANOSECONDS) //测试结果单位
public class MethodBenchmark3 {
    private static final Logger logger = LoggerFactory.getLogger(MethodBenchmark3.class);

    Bean1 bean1;
    Method say;

    public MethodBenchmark3() {
        System.out.println("MethodBenchmark");
    }

    @Setup
    public void setup() throws Exception {
        bean1 = new Bean1();
        say = Bean1.class.getMethod("add", int.class, int.class);
        logger.info("setup..........");
    }

    @Benchmark
    public void test1(Blackhole blackhole) throws Exception {
        int value = bean1.addObj(1, 256);
        blackhole.consume(value);
        //logger.info("test1");
    }

    @Benchmark
    public void test2(Blackhole blackhole) throws Exception {
        Object value = say.invoke(bean1, 1, 256);
        blackhole.consume(value);
        //logger.info("test2");
    }

    @Benchmark
    public void test3(Blackhole blackhole) throws Exception {
        Method say = Bean1.class.getMethod("add", int.class, int.class);
        Object value = say.invoke(bean1, 1, 256);
        blackhole.consume(value);
        //logger.info("test3");
    }

    public static void main(String[] args) throws RunnerException, IOException {
        Options options = new OptionsBuilder().include(MethodBenchmark3.class.getName())
                //.output("benchmark/jedis-Throughput.log")
                .forks(0)
                .build();
        new Runner(options).run();

    }


}
