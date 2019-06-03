/**
 * <p>Title: MyBatisProperties.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.zto.com</p>
 */
package com.example.demo;

import org.springframework.beans.factory.annotation.Value;


public class MyBatisProperties {
    private String name;

    @Value("${mybatis.config.slave.slaveJdbcUrl}")
    private String slaveJdbcUrl;

    private String jdbcUrl;
    private String username;
    private String password;

    private SlaveProperties slave;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlaveJdbcUrl() {
        return slaveJdbcUrl;
    }

    public void setSlaveJdbcUrl(String slaveJdbcUrl) {
        this.slaveJdbcUrl = slaveJdbcUrl;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SlaveProperties getSlave() {
        return slave;
    }

    public void setSlave(SlaveProperties slave) {
        this.slave = slave;
    }
}
