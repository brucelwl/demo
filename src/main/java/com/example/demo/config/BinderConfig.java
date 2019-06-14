package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by bruce on 2019/6/15 00:20
 */
@Component
public class BinderConfig implements EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(BinderConfig.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * test1 org.springframework.boot.context.properties.bind.Binder
     */
    @PostConstruct
    public void binderTest() {
        ShardingProperties sharding = Binder.get(environment).bind("sharding", ShardingProperties.class).orElse(null);

        List<MyDataSourceProperties> myDataSourceProperties = Binder.get(environment).bind("sharding.data-sources", Bindable.listOf(MyDataSourceProperties.class)).orElse(null);

        try {
            Assert.notNull(sharding.getDataSources().get(0).getSlave(), "why slave is null !!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Assert.notNull(myDataSourceProperties.get(0).getSlave(), "Nesting of the same entity class is not supported !!!" +
                    " please see class of com.example.demo.config.MyDataSourceProperties");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
