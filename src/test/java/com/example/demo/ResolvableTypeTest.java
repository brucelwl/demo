package com.example.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Created by bruce on 2019/11/27 9:30
 */
public class ResolvableTypeTest {



    public static void main(String[] args) {

        ResolvableType generic = ResolvableType.forClass(ListenerA.class).as(ApplicationListener.class).getGeneric();

        System.out.println(generic);


        Class<?> aClass = GenericTypeResolver.resolveTypeArgument(ListenerA.class, ApplicationListener.class);

        System.out.println(aClass);

        boolean anyMatch = Arrays.stream(new String[]{})
                .anyMatch((fileExtension) -> StringUtils.endsWithIgnoreCase("classpath:aa.", fileExtension));

        System.out.println(anyMatch);

    }



}
