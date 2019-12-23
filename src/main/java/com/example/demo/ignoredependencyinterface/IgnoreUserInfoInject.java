package com.example.demo.ignoredependencyinterface;

import com.example.demo.config.UserInfo;

/**
 * Created by bruce on 2019/12/2 20:50
 */
public interface IgnoreUserInfoInject {

    /**
     * 必须是标准的Setter方法
     * @param userInfo
     */
    void setUserInfo(UserInfo userInfo);

}
