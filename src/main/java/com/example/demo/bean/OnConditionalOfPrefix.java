package com.example.demo.bean;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by bruce on 2020/4/24 13:42
 */
public class OnConditionalOfPrefix extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(ConditionalOnPrefix.class.getName()));

        String prefix = annotationAttributes.getString("prefix");
        boolean havingValue = annotationAttributes.getBoolean("havingValue");
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();

        MutablePropertySources propertySources = environment.getPropertySources();

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
                                System.out.println(propertyName + " true");
                                temp = propertyName;
                            }
                        }
                    }
                }
            }
        }

        if (temp != null) {
            return new ConditionOutcome(true,
                    ConditionMessage.forCondition(ConditionalOnPrefix.class)
                            .because(temp + " is match"));
        }

        return new ConditionOutcome(false,
                ConditionMessage.forCondition(ConditionalOnPrefix.class)
                        .because("no property prefix is " + prefix));
    }


}
