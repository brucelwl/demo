package com.example.demo;

import com.example.demo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

/**
 * Created by bruce on 2019/6/3 22:08
 */
@Component
public class BinderConfig implements EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(BinderConfig.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        Binder binder = Binder.get(environment);
        BindResult<MyBatisProperties> bindResult = binder.bind("mybatis.config", Bindable.of(MyBatisProperties.class), new BindHandler() {
            @Override
            public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
                Value value = target.getAnnotation(Value.class);
                if (value != null) {
                    Field stringField = ReflectionUtils.findField(name.getClass(), "string");
                    ReflectionUtils.makeAccessible(stringField);
                    ReflectionUtils.setField(stringField, name, value.value());
                }
                return target;
            }
        });

        logger.info(JsonUtil.toJson(bindResult.get()));
    }

}
