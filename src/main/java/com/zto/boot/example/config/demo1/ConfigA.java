package com.zto.boot.example.config.demo1;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bruce on 2019/6/24 22:06
 */
@Configuration
public class ConfigA implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    public ConfigA(){
        //System.out.println("ConfigA create");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
