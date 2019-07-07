package com.zto.boot.example.postprocessor.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *     允许自定义修改应用程序上下文的bean定义，调整上下文的基础bean工厂的bean属性值。
 * 应用程序上下文可以在其bean定义中自动检测BeanFactoryPostProcessor bean，并在创建任何其他bean之前应用它们。
 * 对于以系统管理员为目标的自定义配置文件非常有用，这些文件覆盖在应用程
 * 请参阅PropertyResourceConfigurer及其针对此类配置需求的开箱即用解决方案的具体实现。
 * BeanFactoryPostProcessor可以与bean定义交互并修改bean定义，但绝不能与bean实例交互。
 * 这样做可能会导致bean过早实例化，违反容器并导致意外的副作用。
 * 如果需要bean实例交互，请考虑实现BeanPostProcessor。
 *
 * </pre>
 * Created by bruce on 2019/7/5 10:03
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "custom.beanfactory.post.processor.enable", havingValue = "true")
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * <pre>
     *     在应用程序上下文的标准初始化之后修改其内部bean factory。
     *     所有bean definitions 都将被加载，但还没有实例化bean。
     *     这样就可以覆盖或添加属性.
     * </pre>
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        log.info("BeanFactoryPostProcessor postProcessBeanFactory");

    }

}
