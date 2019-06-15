package com.example.demo.config;

import com.example.demo.bind.MyBinder;
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


        MyDataSourceProperties shardingProperties = Binder.get(environment).bind("sharding.data-sources[0]", MyDataSourceProperties.class).orElse(null);

        MyDataSourceProperties slave = shardingProperties.getSlave();

        System.out.println(slave == null);


        MyDataSourceProperties myDataSourceProperties1 = MyBinder.get(environment)
                .bind("sharding.data-sources[0]", Bindable.of(MyDataSourceProperties.class), null, true)
                .orElse(null);

        System.out.println(myDataSourceProperties1.getSlave() != null);


        ShardingProperties sharding1 = MyBinder.get(environment)
                .bind("sharding", Bindable.of(ShardingProperties.class), null, true)
                .orElse(null);

        System.out.println(sharding1.getDataSources().get(0).getSlave() != null);


    }


}
