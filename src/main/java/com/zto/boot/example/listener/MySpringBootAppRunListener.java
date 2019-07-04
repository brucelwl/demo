package com.zto.boot.example.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by bruce on 2019/7/4 15:14
 */
public class MySpringBootAppRunListener implements SpringApplicationRunListener {

    private SpringApplication application;
    private String[] programArgs;

    /**
     * 构造方法必须要这两个参数
     *
     * @param application SpringApplication
     * @param args        应用程序命令行参数
     */
    public MySpringBootAppRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.programArgs = args;
    }


    @Override
    public void starting() {
        //应用刚启动,ConfigurableEnvironment,ConfigurableApplicationContext实例对象都未创建

        //添加监听器,这里添加的
        //application.setListeners();
        //application.addListeners();

        //添加初始化其
        //application.setInitializers();
        //application.addInitializers();
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

        //environment 已经添加了配置文件中的属性值
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        //spring boot2.1 之后添加 ApplicationContextInitializedEvent

        //执行该方法之前已完成的工作
        //context中可以获取到environment
        //ConfigurableEnvironment environment = context.getEnvironment();

        //数据类型转换其已经添加,默认使用 ApplicationConversionService.getSharedInstance()
        //context.getBeanFactory().getConversionService();

        //ApplicationContextInitializer 接口已经回调完成

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        //此时可以获取应用程序启动参数ApplicationArguments

        //错误方式,
        //此时还没有执行 ((AbstractApplicationContext) applicationContext).refresh()
        //因此调用getBean(...)报错,has not been refreshed yet
        //ApplicationArguments appArguments = context.getBean("springApplicationArguments", ApplicationArguments.class);

        //正确方式
        //ApplicationArguments appArguments = context.getBeanFactory().getBean("springApplicationArguments", ApplicationArguments.class);
        //System.out.println(appArguments);

        //该方法执行完成后则执行 ((AbstractApplicationContext) applicationContext).refresh()
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        //应用程序已经启动,所有非懒加载的bean已经被实例化,但是如下两个接口还没有被回调,紧接着就是执行如下接口方法
        //ApplicationRunner
        //CommandLineRunner
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        //应用程序正常执行监听连接
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        //SpringBoot 启动过程中所有Throwable异常都将执行
    }
}
