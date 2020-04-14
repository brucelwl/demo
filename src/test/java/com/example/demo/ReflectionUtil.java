package com.example.demo;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bruce on 2020/4/10 15:26
 */
public class ReflectionUtil {

    //用户缓存方法引用与字段Field之间的关系
    private static Map<SerializableFunction<?, ?>, Field> cache = new ConcurrentHashMap<>();

    public static <T, R> String getFieldName(SerializableFunction<T, R> function) {
        Field field = ReflectionUtil.getField(function);
        return field.getName();
    }

    /** 缓存方法引用对应的字段,避免每次反射获取 */
    public static Field getField(SerializableFunction<?, ?> function) {
        return cache.computeIfAbsent(function, ReflectionUtil::findField);
    }

    /**
     * 通过反射获取SerializedLambda对象,该对象中保存了方法引用的所代表的method name
     */
    private static Field findField(SerializableFunction<?, ?> function) {
        Field field = null;
        String fieldName = null;
        try {
            //问题1: Class类中并定义writeReplace()方法,这里为什么能反射获取到该方法
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);

            //问题2: 反射获取调用writeReplace()方法
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);

            String implMethodName = serializedLambda.getImplMethodName();
            if (implMethodName.startsWith("get") && implMethodName.length() > 3) {
                fieldName = Introspector.decapitalize(implMethodName.substring(3));

            } else if (implMethodName.startsWith("is") && implMethodName.length() > 2) {
                fieldName = Introspector.decapitalize(implMethodName.substring(2));
            }

            String declaredClass = serializedLambda.getImplClass().replace("/", ".");
            Class<?> aClass = Class.forName(declaredClass, false, ClassUtils.getDefaultClassLoader());

            //Spring 中的反射工具类
            field = ReflectionUtils.findField(aClass, fieldName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (field != null) {
            return field;
        }
        throw new NoSuchFieldError(fieldName);
    }
}
