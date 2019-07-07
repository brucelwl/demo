package com.zto.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bruce on 2019/7/5 22:21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserService3Test {

    //@Autowired
    //private UserService3 userService;


    @Test
    public void test() {
        //System.out.println(userService);
    }


    public void test2() {

        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setParentName("parentName");
        //genericBeanDefinition.
        //genericBeanDefinition.p

        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("parentName");
        childBeanDefinition.setParentName("");

    }


}
