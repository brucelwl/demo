package com.example.demo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by bruce on 2019/11/21 15:26
 */
public class SpringApplicationListener implements GenericApplicationListener {

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.resolve() == ApplicationStartingEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ApplicationStartingEvent startingEvent = (ApplicationStartingEvent) event;

        SpringApplication springApplication = startingEvent.getSpringApplication();

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("def_property1", "value1");
        properties.put("def_property2", "value2");
        properties.put("def_property3", "value3");

        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("classpath:json_propertysource.json");
        try {
            InputStream inputStream = resource.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            String jsonValue = FileCopyUtils.copyToString(reader);

            properties.put(SpringApplicationJsonEnvironmentPostProcessor.SPRING_APPLICATION_JSON_PROPERTY, jsonValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        springApplication.setDefaultProperties(properties);

        springApplication.setAdditionalProfiles("dev1", "dev2");

    }
}
