package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Plugin implements InvocationHandler {

    private final Object target;

    private Plugin(Object target) {
        this.target = target;
    }

    public static Object wrap(Object target) {
        Class<?> type = target.getClass();
        Class<?>[] interfaces = getAllInterfaces(type);
        if (interfaces.length > 0) {
            return Proxy.newProxyInstance(type.getClassLoader(), interfaces, new Plugin(target));
        }
        return target;
    }

    private static Class<?>[] getAllInterfaces(Class<?> type) {
        Set<Class<?>> interfaces = new HashSet<>();
        while (type != null) {
            interfaces.addAll(Arrays.asList(type.getInterfaces()));
            type = type.getSuperclass();
        }
        return interfaces.toArray(new Class<?>[0]);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }


}
