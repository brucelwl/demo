package com.example.demo.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by bruce on 2019/6/23 20:24
 */
public class Beans {

    public static void main(String[] args) {
        Bean beanImpl = new BeanImpl();
        beanImpl.addSampleProperty("呵呵二号");
        System.out.println(beanImpl.getSampleProperty());

        Bean bean = newInstance(Bean.class);

        bean.addSampleProperty("aaaa");

        System.out.println(bean.getSampleProperty());

    }

    public static <T> T newInstance(Class<T> clazz) {
        MyMethodInterceptor2 myMethodInterceptor = new MyMethodInterceptor2();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        //enhancer.setCallbackFilter(new );
        enhancer.setCallback(myMethodInterceptor);
        T bean = (T) enhancer.create();
        return bean;
    }

    static class MyMethodInterceptor2 implements MethodInterceptor {
        private Object value;

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.getName().equals("addSampleProperty")) {
                value = args[0];
            }
            if (method.getName().equals("getSampleProperty")) {
                return value;
            }
            if (method.getName().equals("hashCode")) {
                return System.identityHashCode(obj);
            }

            if (method.getName().equals("toString")) {
                return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
            }
            return null;
        }
    }

    static class MyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object retValFromSuper = null;
            try {
                if (!Modifier.isAbstract(method.getModifiers())) {
                    retValFromSuper = proxy.invokeSuper(obj, args);
                }

                if (method.getName().equalsIgnoreCase("addSampleProperty")) {
                    Bean bean = (Bean) obj;
                    bean.sampleProperty = (String) args[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retValFromSuper;
        }
    }


}
