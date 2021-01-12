package com.bruce.netty.rpc.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 6271330872494117382L;

    public UserInfo() {
    }

    public UserInfo(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private String username;

    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
