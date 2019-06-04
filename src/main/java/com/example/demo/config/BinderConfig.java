package com.example.demo.config;

import com.example.demo.bind.MyBindHandler;
import com.example.demo.bind.MyBinder;
import com.example.demo.config.annotation.AttributeKey;
import com.example.demo.config.annotation.NestedAttribute;
import com.example.demo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.lang.reflect.InvocationTargetException;

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

    /**
     * test1 org.springframework.boot.context.properties.bind.Binder
     */
    @PostConstruct
    public void binderTest() {
        Binder binder = Binder.get(environment);
        BindResult<MyBatisProperties> bindResult = binder.bind("mybatis.config", Bindable.of(MyBatisProperties.class), new BindHandler() {
            @Override
            public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
                AttributeKey key = target.getAnnotation(AttributeKey.class);
                if (key != null) {
                    Field stringField = ReflectionUtils.findField(name.getClass(), "string");
                    ReflectionUtils.makeAccessible(stringField);
                    ReflectionUtils.setField(stringField, name, key.value());
                }
                return target;
            }
        });

        logger.info(JsonUtil.toJson(bindResult.get()));
    }

    /**
     * test2 MyBinder
     */
    @PostConstruct
    public void MyBinderTest() {
        com.example.demo.bind.BindResult<MyBatisProperties> bind = MyBinder.get(environment)
                .bind("mybatis.config", Bindable.of(MyBatisProperties.class), new MyBindHandler() {
                    @Override
                    public Object onFinish(ConfigurationPropertyName name, Bindable<?> target, com.example.demo.bind.BindContext context, Object result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
                        AttributeKey key = target.getAnnotation(AttributeKey.class);
                        if (key != null) {
                            try {
                                return environment.resolveRequiredPlaceholders(key.value());
                            } catch (IllegalArgumentException a) {
                                return result;
                            }
                        }
                        return result;
                    }

                    @Override
                    public boolean onNoDescendSkip(ConfigurationPropertyName name, Bindable<?> target, com.example.demo.bind.BindContext context) {
                        return target.getAnnotation(NestedAttribute.class) == null;
                    }
                });

        logger.info(JsonUtil.toJson(bind.get()));
    }

}
