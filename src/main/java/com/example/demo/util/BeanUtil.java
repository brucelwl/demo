package com.example.demo.util;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by bruce on 2020/4/22 21:22
 */
public class BeanUtil {

    private static final ConcurrentReferenceHashMap<CacheKey, List<CopyMethodHandle>> methodHandleCache = new ConcurrentReferenceHashMap<>();
    private static final ConcurrentReferenceHashMap<CacheKey, BeanCopier> copierCache = new ConcurrentReferenceHashMap<>();

    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    //没必要对BeanCopier进行缓存 ???

    /** 只能支持标准的Getter/Setter */
    public static <T> T cglibCopy(Object source, T target) {
        BeanCopier copier = copierCache.computeIfAbsent(new CacheKey(source.getClass(), target.getClass()),
                cacheKey -> BeanCopier.create(source.getClass(), target.getClass(), false));
        //BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
        return target;
    }

    /** 只能支持标准的Getter/Setter */
    public static <T> T cglibCopy(Object source, Class<T> target) {
        BeanCopier copier = copierCache.computeIfAbsent(new CacheKey(source.getClass(), target),
                cacheKey -> BeanCopier.create(source.getClass(), target, false));
        //BeanCopier copier = BeanCopier.create(source.getClass(), target, false);
        T t = null;
        try {
            t = target.newInstance();
            copier.copy(source, t, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T copy(Object source, T target) {
        List<CopyMethodHandle> getSetMethodHandles = methodHandleCache
                .computeIfAbsent(new CacheKey(source.getClass(), target.getClass()),
                        key -> getCopyMethodHandles(source, target));
        try {
            for (CopyMethodHandle getSetMethodHandle : getSetMethodHandles) {
                getSetMethodHandle.invoke(source, target);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return target;
    }

    private static <T> List<CopyMethodHandle> getCopyMethodHandles(Object source, T target) {
        ArrayList<CopyMethodHandle> methodHandles = new ArrayList<>();
        PropertyDescriptor[] sourcePropertyDescs = getPropertyDescs(source.getClass());
        Map<String, PropertyDescriptor> targetPropertyDescsMap = getPropertyDescsMap(target.getClass());

        for (PropertyDescriptor sourcePropertyDesc : sourcePropertyDescs) {
            Method readMethod = sourcePropertyDesc.getReadMethod();
            if (readMethod != null) {
                PropertyDescriptor targetPropertyDesc = targetPropertyDescsMap.get(sourcePropertyDesc.getName());
                Method writeMethod = null;
                if (targetPropertyDesc != null
                        && (writeMethod = targetPropertyDesc.getWriteMethod()) != null
                        && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        MethodHandle getMethodHandle = MethodHandles.lookup().unreflect(readMethod);
                        MethodHandle setMethodHandle = MethodHandles.lookup().unreflect(writeMethod);

                        methodHandles.add(new CopyMethodHandle(getMethodHandle, setMethodHandle));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return methodHandles;
    }

    public static class CacheKey {
        private Class<?> source;
        private Class<?> target;

        public CacheKey(Class<?> source, Class<?> target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(source, cacheKey.source) && Objects.equals(target, cacheKey.target);
        }

        @Override
        public int hashCode() {
            //减少方法调用栈
            //return Objects.hash(source, target);
            return Arrays.hashCode(new Object[]{source, target});
        }
    }

    static class CopyMethodHandle {
        private MethodHandle sourceGet;
        private MethodHandle targetSet;

        public CopyMethodHandle(MethodHandle sourceGet, MethodHandle targetSet) {
            this.sourceGet = sourceGet;
            this.targetSet = targetSet;
        }

        public void invoke(Object source, Object target) throws Throwable {
            Object value = sourceGet.invoke(source);
            targetSet.invoke(target, value);
        }
    }

    private static PropertyDescriptor[] getPropertyDescs(Class<?> clazz) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (beanInfo == null || beanInfo.getPropertyDescriptors() == null) {
            return new PropertyDescriptor[0];
        }
        return beanInfo.getPropertyDescriptors();
    }

    private static Map<String, PropertyDescriptor> getPropertyDescsMap(Class<?> clazz) {
        return Arrays.stream(getPropertyDescs(clazz))
                .collect(Collectors.toMap(PropertyDescriptor::getName, (desc) -> desc));
    }

}
