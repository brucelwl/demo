//package com.zto.boot.example.config;
//
//import org.springframework.boot.autoconfigure.http.HttpProperties;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import static org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME;
//
///**
// * @see org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
// * Created by bruce on 2019/7/12 22:31
// */
//@Configuration
//public class DispatcherServletConfig {
//
//
//    @Bean(name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
//    public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties, HttpProperties httpProperties) {
//        DispatcherServlet dispatcherServlet = new DispatcherServlet();
//        dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
//        dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(webMvcProperties.isThrowExceptionIfNoHandlerFound());
//        dispatcherServlet.setEnableLoggingRequestDetails(httpProperties.isLogRequestDetails());
//
//        dispatcherServlet.setPublishEvents(false);
//
//        return dispatcherServlet;
//    }
//
//
//}
