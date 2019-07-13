package com.zto.boot.example;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by bruce on 2019/7/12 10:06
 */
public class JarTest {

    private URL[] getClasspath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader instanceof URLClassLoader) {
            URL[] urLs = ((URLClassLoader) classLoader).getURLs();
            return urLs;
        }
        return null;
    }


    @Test
    public void test() {
        URL[] classpath = getClasspath();
        for (URL url : classpath) {
            System.out.println(url);
        }

    }


}
