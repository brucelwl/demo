package com.example.demo.cglib;

import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by bruce on 2019/6/23 21:54
 */
public class ProxyInvoke {

    public static BeanI create() {
        return (BeanI) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{BeanI.class}, new MyInvocationHandler());
    }

    static class MyInvocationHandler implements InvocationHandler {

        private Object value;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("addSampleProperty")) {
                value = args[0];
            } else if (method.getName().equals("getSampleProperty")) {
                return value;
            }
            return null;
        }
    }


}
