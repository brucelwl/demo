package com.zto.boot.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.time.LocalDateTime;

/**
 * <pre>
 *  SpringBoot 应用中可广播的事件类型:
 *  org.springframework.boot.context.event.ApplicationStartingEvent
 *  org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
 *  org.springframework.boot.context.event.ApplicationContextInitializedEvent
 *  org.springframework.boot.context.event.ApplicationPreparedEvent(发布该事件之前判断是否实现ApplicationContextAware,并回调)
 *  org.springframework.context.event.ContextRefreshedEvent (Spring)
 *  org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
 *  org.springframework.boot.context.event.ApplicationStartedEvent
 *  org.springframework.boot.context.event.ApplicationReadyEvent(ApplicationFailedEvent)
 *  org.springframework.context.event.ContextClosedEvent (Spring)
 *
 * </pre>
 *
 * <pre>
 * 1 通过spring.factories自动配置监听器,可以监听所有的事件,方式如下:
 *   org.springframework.context.ApplicationListener=\
 *   com.zto.boot.example.listener.MyApplicationEventListener
 *
 * 2 在配置文件通过context.listener.classes属性指定监听器,会由
 *   org.springframework.boot.context.config.DelegatingApplicationListener 在监听到
 *   ApplicationEnvironmentPreparedEvent事件后,读取配置的类反射实例化,
 *   排序后添加其成员变量广播器中multicaster(SimpleApplicationEventMulticaster),然后调用监听器的回调方法
 *   因此这种方式只能监听ApplicationEnvironmentPreparedEvent以及之后的事件类型
 *
 * 通过spring.factories和context.listener.classes配置的监听器,注意点:
 *
 *  1 不需要再加@Component及其子类注解,例如@Configuration,否则Spring在执行类文件扫描的时候这些注解类还会被扫描一次,导致创建两个监听器实例对象
 *
 *  2 使用条件注解无效
 *
 *  3 不会放到Spring IOC中(DefaultSingletonBeanRegistry),无法通过 @Autowired, @Resource, @Inject等注解注入,无法通过getBean()获取
 *   仅是保存在AbstractApplicationContext的成员变量Set<ApplicationListener> applicationListeners
 *
 *  4 除了ApplicationContextAware接口外,不支持其他Aware接口,而且仅仅在ApplicationPreparedEvent时才会执行Aware接口
 *
 *  5 可排序
 *
 * </pre>
 * <pre>
 *  org.springframework.boot.context.logging.LoggingApplicationListener用于日志初始化,
 *  发布ApplicationStartingEvent事件时日志不可用,
 *  LoggingApplicationListener监听到ApplicationEnvironmentPreparedEvent完成日志初始化
 *  由于该监听器排序级别比较高,默认为 Ordered.HIGHEST_PRECEDENCE + 20
 *  所以在其后执行的监听器可以使用日志
 * </pre>
 * <pre>
 *  org.springframework.boot.context.event.ApplicationPreparedEvent 以及之前的事件
 *  和ApplicationPreparedEvent之后的事件是由不同的事件广播器广播
 * </pre>
 * <p>
 * Created by bruce on 2019/7/2 10:36
 */
//@Configuration
//@ConditionalOnProperty(name = "enable.custom.eventListener", havingValue = "true")
public class MyApplicationEventListener5 implements ApplicationListener<SpringApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationEventListener.class);
    //DeferredLog deferredLog = new DeferredLog();

    public MyApplicationEventListener5() {
        logPrint(" create...");
    }

    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {

        if (event instanceof ApplicationStartedEvent) {
            //ContextRefreshedEvent
            //ContextClosedEvent 应用关闭时执行

            logPrint(event.getClass().getName());

        } else {
            logger.info("............" + event.getClass().getName());
        }

    }

    private void logPrint(String mesg) {
        System.out.println(LocalDateTime.now().toString()
                + String.format("%-29s", "  sout") + Thread.currentThread().getName() + "} "
                + String.format("%-40s", MyApplicationEventListener.class.getSimpleName()) + " : " + mesg);
    }


}
