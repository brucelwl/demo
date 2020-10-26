package com.example.demo.methodhandle;

import com.example.demo.util.MethodHandlesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class MyInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyInvocationHandler.class);

    ConcurrentHashMap<Method, MethodHandle> methodHandleMap = new ConcurrentHashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            MethodHandle defaultMethodHandle = methodHandleMap.computeIfAbsent(method, key -> {
                MethodHandle methodHandle = MethodHandlesUtil.getSpecialMethodHandle(method);
                return methodHandle.bindTo(proxy);
            });
            logger.info("default 接口方法:{}", method.getName());
            return defaultMethodHandle.invokeWithArguments(args);
        }

        logger.info("普通接口方法:{}", method.getName());
        //做其他逻辑处理,

        //模拟数据
        return "这是一个普通接口方法:" + method.getName();
    }
}
