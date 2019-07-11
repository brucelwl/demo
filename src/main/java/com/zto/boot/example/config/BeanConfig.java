package com.zto.boot.example.config;

import com.zto.boot.example.service.UserService3;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by bruce on 2019/7/1 10:23
 */
//@Configuration
@Component
public class BeanConfig implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        String aaaa = environment.getProperty("user.password");
    }

    //@Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @Bean(initMethod = "customInitMethod", destroyMethod = "customDestroyMethod")
    public UserService3 userService3() {
        UserService3 userService3 = new UserService3();
        return userService3;
    }


}
