package com.example.demo.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2020/4/24 10:18
 */
@Component
public class Acongi implements EnvironmentAware, InitializingBean {

    private ConfigurableEnvironment env;

    public Acongi() {
        System.out.println("Acongi...初始化");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MutablePropertySources propertySources = env.getPropertySources();

        String prefix = "spring.redis";
        boolean havingValue = true;
        String temp = null;

        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof EnumerablePropertySource) {
                EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
                String[] propertyNames = enumerablePropertySource.getPropertyNames();
                for (String propertyName : propertyNames) {
                    if (propertyName.startsWith(prefix)) {
                        if (havingValue) {
                            Object property = enumerablePropertySource.getProperty(propertyName);
                            if (property != null) {
                                //System.out.println(propertyName + " true");
                                temp = propertyName;
                            }
                        }
                    }
                }
            }
        }

        if (temp != null) {
            System.out.println(temp + " is match");
        } else {
            System.out.println("no property prefix is " + prefix);
        }

        PropertySource<?> configurationProperties = propertySources.get("configurationProperties");
        OriginLookup<String> originLookup = (OriginLookup<String>) configurationProperties;

        //查找这个属性在哪哥配置文件
        Origin origin = originLookup.getOrigin("spring.redis.timeout");
        System.out.println(origin);

        //判断是否存在指定后缀的属性
        ConfigurationPropertyName prefixName = ConfigurationPropertyName.of(prefix);
        Iterable<ConfigurationPropertySource> configurationPropertySources = ConfigurationPropertySources.get(env);
        for (ConfigurationPropertySource configurationPropertySource : configurationPropertySources) {
            ConfigurationPropertyState configurationPropertyState = configurationPropertySource.containsDescendantOf(prefixName);

            Object underlyingSource = configurationPropertySource.getUnderlyingSource();

            System.out.println(configurationPropertyState + " " + underlyingSource.toString());
        }

        RedisProperties redisProperties = Binder.get(env).bind(prefix, RedisProperties.class).orElse(null);
        System.out.println(redisProperties);

    }
}
