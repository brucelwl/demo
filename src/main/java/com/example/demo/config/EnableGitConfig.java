package com.example.demo.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bruce on 2019/8/1 22:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGitVersionControlConfig
public @interface EnableGitConfig {

    @AliasFor(value = "serverAddress", annotation = EnableGitVersionControlConfig.class)
    String address() default "";

    @AliasFor(value = "targetBranch", annotation = EnableGitVersionControlConfig.class)
    String branch() default "";

}
