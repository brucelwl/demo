package com.example.demo.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bruce on 2019/8/1 22:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GitConfigBeanDefinitionRegistrar.class)
public @interface EnableGitVersionControlConfig {

    String serverAddress() default "";

    String targetBranch() default "";

}
