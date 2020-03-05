package com.zto.boot.example.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

/**
 * Created by bruce in 2020/2/16 18:08
 */
@Component
@Endpoint(id = "hello")
public class HelloEndpoint implements HelloEndpointParent{

    @ReadOperation
    @WriteOperation
    @DeleteOperation
    public String haha(){
        return "哈哈哈哈哈哈";
    }

    //@ReadOperation
    public String hehe(){
        return "呵呵呵呵";
    }


}
