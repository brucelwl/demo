package com.example.demo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * -Xms10m -Xmx10m -XX:MetaspaceSize=10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -version
 * <p>
 * Created by bruce on 2019/11/27 20:47
 */
public class CommonTest {
    //static {
    //    System.setProperty("jdk.internal.lambda.dumpProxyClasses", ".");
    //}

    public static void main(String[] args) throws InterruptedException {
        ArrayList<SerializableFunction<People, String>> functions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            SerializableFunction<People, String> getName = People::getName;
            functions.add(getName);
        }

        System.out.println(functions.get(0) == functions.get(1));
        System.out.println(functions.get(1) == functions.get(2));
        System.out.println(functions.get(2) == functions.get(3));
        System.out.println(functions.get(3) == functions.get(4));

        CommonTest commonTest = new CommonTest();
        commonTest.test(People::getName, People::getAge);


        List<String> strings = Arrays.asList(new String[]{"DubboService", "URL"});

        System.out.println(strings);


    }

    public <R, T> void test(SerializableFunction<R, T>... funs) {




    }


}
