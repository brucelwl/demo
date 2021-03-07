package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 2020/5/9 23:39
 */
@Configuration(proxyBeanMethods = false)
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ArrayList<HttpMessageConverter<?>> newConverters = new ArrayList<>(converters.size());

        //TODO 去除冗余的HttpMessageConverter
        for (HttpMessageConverter<?> converter : converters) {
            if (newConverters.stream().noneMatch(convert -> convert.getClass() == converter.getClass())){
                newConverters.add(converter);
            }
        }
        converters.clear();
        converters.addAll(newConverters);
    }
}
