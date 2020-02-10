package com.example.demo;

import com.example.demo.config.Address;
import com.example.demo.config.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.loader.ExecutableArchiveLauncher;
import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.loader.MainMethodRunner;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * SpringBoot打成jar包后,真正的jar启动类是{@link JarLauncher},
 * 通过反射调用应用启动类的main(String[] args),
 * 实则如果SpringBoot定义应用启动类的启动方法也是可以的
 * @see ExecutableArchiveLauncher#getMainClass()
 * @see MainMethodRunner#run()
 */
@ServletComponentScan
//@EnableGitConfig(address = "http://localhost:454",branch = "dev")
@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        UserInfo bean = context.getBean(UserInfo.class);
        System.out.println(bean);

        System.out.println(DemoApplication.class.getClassLoader());

        List<Address> addresses = bean.getAddresses();
        for (Address address : addresses) {
            System.out.println(address);
        }

        //TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        //tomcatServletWebServerFactory.setProtocol(Http11AprProtocol.class.getName());

    }

}
