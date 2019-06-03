package com.example.demo;

/**
 * Created by bruce on 2019/6/3 19:26
 */
public class SlaveProperties {

    private String slaveJdbcUrl;
    private String slaveUserName;
    private String slavePassword;
    private Boolean slaveReadOnly;


    public String getSlaveJdbcUrl() {
        return slaveJdbcUrl;
    }

    public void setSlaveJdbcUrl(String slaveJdbcUrl) {
        this.slaveJdbcUrl = slaveJdbcUrl;
    }

    public String getSlaveUserName() {
        return slaveUserName;
    }

    public void setSlaveUserName(String slaveUserName) {
        this.slaveUserName = slaveUserName;
    }

    public String getSlavePassword() {
        return slavePassword;
    }

    public void setSlavePassword(String slavePassword) {
        this.slavePassword = slavePassword;
    }

    public Boolean getSlaveReadOnly() {
        return slaveReadOnly;
    }

    public void setSlaveReadOnly(Boolean slaveReadOnly) {
        this.slaveReadOnly = slaveReadOnly;
    }
}
