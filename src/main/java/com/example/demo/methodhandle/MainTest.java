package com.example.demo.methodhandle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainTest {

    public static void main(String[] args) {

        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserServiceInvoke.class.getClassLoader(),
                new Class[]{UserMapper.class}, new MyInvocationHandler());

        String aa = userMapper.getUserName("aa", true, 18);
        System.out.println(aa);
    }


    static class MyInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(proxy, args);
        }
    }


}
