package com.example.demo.methodhandle;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2019/6/23 21:26
 */
@BenchmarkMode(Mode.AverageTime) //测试的模式,可以测试吞吐,延时等;
@Warmup(iterations = 1, time = 2)  //预热的循环次数
@Threads(15)  //开启多少条线程测试
@State(Scope.Benchmark) //@State(Scope.Benchmark) 一个测试启用实例, @State(Scope.Thread) 每个线程启动一个实例
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 2, batchSize = 1024)
@OutputTimeUnit(TimeUnit.NANOSECONDS) //测试结果单位
public class MethodHandleBenchmarkTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandleBenchmarkTest.class);

   static UserMapper userMapperInvoke;
    UserMapper userMapperBindToInvoke;

    @Setup
    public void setup() throws Exception {
        userMapperInvoke = UserServiceInvoke.getInstance(new UserServiceInvoke.OptimizeMethodHandleInvoke(UserMapper.class));
        userMapperBindToInvoke = UserServiceInvoke.getInstance(new UserServiceInvoke.MybatisMethodHandleInvoke(UserMapper.class));
    }

    @Benchmark
    public void mybatisMethodHandleInvoke(Blackhole blackhole) throws Throwable {
        String name = userMapperBindToInvoke.getUserName("lwl", true, 15);
        blackhole.consume(name);
    }

    @Benchmark
    public void optimizeMethodHandleInvoke(Blackhole blackhole) throws Throwable {
        String o = userMapperInvoke.getUserName("lwl", true, 15);
        blackhole.consume(o);
    }

    public static void main(String[] args) throws Exception {
        //Options options = new OptionsBuilder().include(MethodHandleBenchmarkTest.class.getName())
        //        //.output("benchmark/jedis-Throughput.log")
        //        .forks(0)
        //        .build();
        //new Runner(options).run();

        userMapperInvoke = UserServiceInvoke.getInstance(new UserServiceInvoke.OptimizeMethodHandleInvoke(UserMapper.class));
        String name = userMapperInvoke.getUserName("lwl", true, 15);
        System.out.println(name);
    }

}
