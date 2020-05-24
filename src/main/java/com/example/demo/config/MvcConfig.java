package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 2020/5/9 23:39
 */
@Configuration(proxyBeanMethods = false)
public class MvcConfig implements WebMvcConfigurer {


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

        //BeanSerializerFactory
        SerializerFactory serializerFactory = objectMapper.getSerializerFactory();

        SerializerFactoryConfig serializerFactoryConfig = new SerializerFactoryConfig();

        objectMapper.setSerializerFactory(new MyBeanSerializerFactory(serializerFactoryConfig));

        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        return mappingJackson2HttpMessageConverter;
    }


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
