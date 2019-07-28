package com.example.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

/**
 *  https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-aj-ltw-spring
 *
 * -javaagent:D:\developertools\maven3.5.4_lwl\repo\org\springframework\spring-instrument\5.1.8.RELEASE\spring-instrument-5.1.8.RELEASE.jar
 * -javaagent:D:\developertools\maven3.5.4_lwl\repo\org\aspectj\aspectjweaver\1.9.4\aspectjweaver-1.9.4.jar
 *
 * <p>
 * Created by bruce on 2019/7/28 17:36
 */
@Configuration
//@EnableLoadTimeWeaving(aspectjWeaving= EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
//@EnableCaching(mode = AdviceMode.PROXY, proxyTargetClass = true)
//@EnableCaching(mode = AdviceMode.PROXY)
@EnableCaching(mode = AdviceMode.ASPECTJ)
//public class AppConfig implements LoadTimeWeavingConfigurer {
public class AppConfig {

    //@Override
    //public LoadTimeWeaver getLoadTimeWeaver() {
    //    return new InstrumentationLoadTimeWeaver();
    //}


}
