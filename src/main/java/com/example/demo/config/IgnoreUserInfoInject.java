package com.example.demo.config;

/**
 * Created by bruce in 2020/5/21 23:59
 */
public interface IgnoreUserInfoInject {

    /**
     * 必须是标准的Setter方法
     * @param userInfo
     */
    void setUserInfo(UserInfo userInfo);
}
