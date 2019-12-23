package com.example.demo.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * 自定义ServletContainerInitializer在springboot中无效
 * 可以自己创建TomcatServletWebServerFactory,以编程方式添加
 *
 * javax.servlet.ServletContainerInitializer
 * org.springframework.boot.web.servlet.ServletContextInitializer
 *
 *
 * org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory#setInitParameters(java.util.Map)
 * TomcatServletWebServerFactory
 * JettyServletWebServerFactory
 * UndertowServletWebServerFactory
 *
 * ContextRefreshedEvent事件及之后才能从ApplicationContext中拿到ServletContext
 * ServletContext servletContext = AnnotationConfigServletWebServerApplicationContext#getServletContext();
 * servletContext.setInitParameter("aaa", "aaa")
 *
 *
 * 从BeanFactory容器中可以找到如下Bean
 * WebApplicationContext.SERVLET_CONTEXT_BEAN_NAME              javax.servlet.ServletContext
 * ConfigurableWebApplicationContext.SERVLET_CONFIG_BEAN_NAME   javax.servlet.ServletConfig  默认无该Bean
 * WebApplicationContext.CONTEXT_PARAMETERS_BEAN_NAME           ServletContext#getInitParameter(paramName) 对应的InitParameter Map集合
 * WebApplicationContext.CONTEXT_ATTRIBUTES_BEAN_NAME           servletContext#getAttribute(attrName) 对应的Attribute Map集合
 *
 * Created by bruce on 2019/11/24 20:30
 */
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("com.example.demo.servlet.MyInitializer");
    }
}
