package com.example.demo.copy;

import com.example.demo.util.BeanUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
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
public class BenchmarkTest {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkTest.class);

    public Dept createEntity() {
        Dept source = new Dept();
        source.setCreateTime(new Date());
        source.setDbSource("mongo");
        source.setDeptName("开发部");
        source.setDeptNo(14785L);
        source.setPassword("12345");
        return source;
    }

    @Benchmark
    public void copyProperties(Blackhole blackhole) throws Exception {

        Dept entity = createEntity();
        DeptVo deptVo = BeanUtil.copyProperties(entity, new DeptVo());
        blackhole.consume(deptVo);
    }

    @Benchmark
    public void cglibCopy2(Blackhole blackhole) throws Exception {
        Dept entity = createEntity();
        DeptVo deptVo = BeanUtil.cglibCopy(entity, DeptVo.class);
        blackhole.consume(deptVo);
    }

    @Benchmark
    public void cglibCopy1(Blackhole blackhole) throws Exception {
        Dept entity = createEntity();
        DeptVo deptVo = BeanUtil.cglibCopy(entity, new DeptVo());
        blackhole.consume(deptVo);
    }

    @Benchmark
    public void copy(Blackhole blackhole) throws Exception {
        Dept entity = createEntity();
        DeptVo deptVo = BeanUtil.copy(entity, new DeptVo());
        blackhole.consume(deptVo);
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(BenchmarkTest.class.getName())
                //.output("benchmark/jedis-Throughput.log")
                .forks(0)
                .build();
        new Runner(options).run();


    }

}
