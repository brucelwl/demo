package com.example.demo.config;

import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by bruce on 2019/8/22 16:43
 */
public class UserInfo {

    //private String userName;

    private String password;

    private List<Address> addresses;

    @NumberFormat(pattern = "###,###.##")
    private Double num;

    // 只使用using 不会处理null的情况
    //@JsonSerialize(using = CustomerDoubleSerialize.class, nullsUsing = CustomerDoubleSerialize.class)
    //private double salary;

    private Date brithDay;

    /*public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    /*public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }*/

    public Date getBrithDay() {
        return brithDay;
    }

    public void setBrithDay(Date brithDay) {
        this.brithDay = brithDay;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                //"userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", addresses=" + addresses +
                ", num=" + num +
                //", salary=" + salary +
                ", brithDay=" + brithDay +
                '}';
    }
}
