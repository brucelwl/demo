package com.example.demo.proxy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2019/6/23 21:26
 */

@BenchmarkMode(Mode.AverageTime) //测试的模式,可以测试吞吐,延时等;
@Warmup(iterations = 1)  //预热的循环次数
@Threads(15)  //开启多少条线程测试
@State(Scope.Benchmark) //@State(Scope.Benchmark) 一个测试启用实例, @State(Scope.Thread) 每个线程启动一个实例
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 2, batchSize = 1024)
@OutputTimeUnit(TimeUnit.NANOSECONDS) //测试结果单位
public class ProxyBenchmarkTest {
    private static final Logger logger = LoggerFactory.getLogger(ProxyBenchmarkTest.class);

    ExecutorHandler executorHandler;
    ExecutorHandler executorHandler2;

    @Setup
    public void setup() throws Exception {
        InterceptorChain interceptorChain = new InterceptorChain();
        executorHandler = (ExecutorHandler) interceptorChain.pluginAll(new ExecutorHandlerImpl(1));
        executorHandler2 = (ExecutorHandler) interceptorChain.pluginAll(new ExecutorHandlerImpl(8));
    }

    @Benchmark
    public void jdkProxyN(Blackhole blackhole) throws Exception {
        MappedStatement mappedStatement = new MappedStatement(
                "select * from user info", 5,500,"c://aaa.mapper");

        List<Object> query = executorHandler2.query(mappedStatement, 45);
        blackhole.consume(query);
    }

    @Benchmark
    public void jdkProxy1(Blackhole blackhole) throws Exception {
        MappedStatement mappedStatement = new MappedStatement(
                "select * from user info", 5,500,"c://aaa.mapper");

        List<Object> query = executorHandler.query(mappedStatement, 45);
        blackhole.consume(query);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ProxyBenchmarkTest.class.getName())
                //.output("benchmark/jedis-Throughput.log")
                .forks(0)
                .build();
        new Runner(options).run();

    }

}
