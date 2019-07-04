package com.zto.boot.example.initializer;

import com.zto.boot.example.listener.MyApplicationEventListener4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * <pre>
 *
 * 1 通过 spring.factories 方式配置Initializer
 * org.springframework.context.ApplicationContextInitializer=\
 * com.zto.boot.example.initializer.MyApplicationContextInitializer
 *
 * 2 也可以在配置文件中通过属性context.initializer.classes指定,会由org.springframework.boot.context.config.DelegatingApplicationContextInitializer
 * 在执行initialize(applicationContext)方法时读取,并反射实例化,然后执行initialize(applicationContext)方法,同样不会被放到IOC
 *
 * <b>注意点:<b/>
 *
 * 1 自定义ApplicationContextInitializer,不要加@Component及其子类注解,例如@Configuration
 * 否则将会创建两次实例对象,并且通过注解创建的实例的initialize(...)方法也不会被调用
 *
 * 2 以上两种自动配置的ApplicationContextInitializer不会被放到SpringIOC中,也只有这两种方式
 *
 * 3 条件注解无效,只能程序中自己编码判断
 *
 * 4 可排序
 *
 * 5 不支持Aware接口
 *
 * </pre>
 * Created by bruce on 2019/7/3 16:25
 */
//@ConditionalOnProperty(name = "enable.custom.initializer", havingValue = "true")
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer.class);

    private ConfigurableApplicationContext context;
    private Environment environment;

    public MyApplicationContextInitializer() {
        //logger.info("自定义的ApplicationContextInitializer执行构造方法");
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        this.context = applicationContext;
        //通过ApplicationContext获取environment对象
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        //logger.info("自定义的ApplicationContextInitializer执行回调方法initialize(...)");

        //这时候添加的事件监听器只是保存在AbstractApplicationContext的成员变量Set<ApplicationListener<?>> applicationListeners中,
        //并没有真正执行监听的作用,原因:Spring中事件的广播是通过事件广播器ApplicationEventMulticaster来实现
        //默认情况下,在AbstractApplicationContext.refresh()方法中调用initApplicationEventMulticaster()后才会创建广播器实例,
        //其默认实现类就是SimpleApplicationEventMulticaster,并注入到Spring的IOC,默认bean名称为applicationEventMulticaster
        //当在AbstractApplicationContext.refresh()中调用registerListeners()方法后,才会将applicationListeners中的监听器添加到
        //广播器applicationEventMulticaster中
        //因此这种方式添加的监听器只能监听:org.springframework.context.event.ContextRefreshedEvent 以及之后的事件如下:
        //ContextRefreshedEvent,ServletWebServerInitializedEvent,ApplicationStartedEvent,ApplicationReadyEvent

        applicationContext.addApplicationListener(new MyApplicationEventListener4());

        //能否在initialize(...)方法中通过applicationContext获取广播器实例,直接将事件监听器添加到里面 ?
        //不能,这个时候applicationContext对象刚创建,原因:
        //1 这时候SpringIOC中并没有添加ApplicationEventMulticaster这个Bean
        //2 ConfigurableApplicationContext还没有执行refresh()方法,调用getBean() 抛出异常
        //org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@332729ad has not been refreshed yet
        //ObjectProvider<ApplicationEventMulticaster> beanProvider = applicationContext.getBeanProvider(ApplicationEventMulticaster.class);

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
