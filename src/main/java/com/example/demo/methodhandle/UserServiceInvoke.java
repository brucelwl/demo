package com.example.demo.methodhandle;

import com.example.demo.util.MethodHandlesUtil;
import com.example.demo.test.Son;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserServiceInvoke {

    private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;

    public static UserMapper getInstance(InvocationHandler handler) {
        return (UserMapper) Proxy.newProxyInstance(UserServiceInvoke.class.getClassLoader(),
                new Class[]{UserMapper.class}, handler);
    }

    /**
     * 仅仅是测试,所以固定调用 getUserName 方法
     */
    public static MethodHandle getMethodHandle(Class<?> declaringClass) {

        MethodHandles.Lookup lookup = MethodHandlesUtil.lookup(declaringClass);
        //通过java.lang.invoke.MethodHandles.Lookup.findSpecial获取子类的父类方法的MethodHandle
        //用于调用某个类的父类方法
        MethodHandle virtual = null;
        try {
            MethodType methodType = MethodType.methodType(String.class, String.class, Boolean.class, int.class);
            virtual = lookup.findSpecial(declaringClass, "getUserName", methodType, declaringClass);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return virtual;
    }

    public static MethodHandle getSonMethodHandle(Class<?> cl) {
        MethodHandles.Lookup lookup = MethodHandlesUtil.lookup(cl);
        MethodType mt = MethodType.methodType(void.class);
        MethodHandle thinking = null;
        try {
            thinking = lookup.findSpecial(Son.class, "thinking", mt, Son.class);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return thinking;
    }


    public static class OptimizeMethodHandleInvoke implements InvocationHandler {

        MethodHandle methodHandle;
        private volatile boolean bindTo;

        public OptimizeMethodHandleInvoke(Class<?> declaringClass) {
            methodHandle = getMethodHandle(declaringClass);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!bindTo) {
                synchronized (this) {
                    if (!bindTo) {
                        methodHandle = this.methodHandle.bindTo(proxy);
                        bindTo = true;
                    }
                }
            }
            return methodHandle.invokeWithArguments(args);
        }
    }

    public static class MybatisMethodHandleInvoke implements InvocationHandler {

        MethodHandle methodHandle;

        public MybatisMethodHandleInvoke(Class<?> declaringClass) {
            methodHandle = getMethodHandle(declaringClass);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return methodHandle.bindTo(proxy).invokeWithArguments(args);
        }
    }

}
