package com.zto.boot.example.config;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Created by bruce on 2019/7/1 10:23
 */
@Configuration
public class CacheManagerConfig implements EnvironmentAware {

    @Bean
    public SimpleCacheManager cacheManager(List<Cache> caches){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }


    @Bean
    public ConcurrentMapCacheFactoryBean stockDetail(){
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("stockDetail");
        return cacheFactoryBean;
    }


    @Bean
    public ConcurrentMapCacheFactoryBean detailMsg(){
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("detailMsg");
        return cacheFactoryBean;
    }


    @Override
    public void setEnvironment(Environment environment) {


        String aaaa = environment.getProperty("user.password");


    }
}
