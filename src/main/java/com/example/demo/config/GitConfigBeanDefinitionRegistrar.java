package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by bruce on 2019/8/1 22:59
 */
public class GitConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(GitConfigBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableGitVersionControlConfig.class.getName()));

        String serverAddress = attributes.getString("serverAddress");

        String targetBranch = attributes.getString("targetBranch");

        logger.info("serverAddress={}", serverAddress);
        logger.info("targetBranch={}", targetBranch);

    }
}
