package com.example.demo.copy;

import java.util.Date;

/**
 * Created by bruce on 2020/4/21 20:31
 */
//@Setter
//@Getter
public class Dept {

    private Long deptNo;

    private String deptName;

    private String password;

    private String dbSource;

    private Date createTime;

    public Long getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Long deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {

        //System.out.println("getDeptName........");

        return deptName;
    }

    public void setDeptName(String deptName) {
        //System.out.println("setDeptName........");
        this.deptName = deptName;
    }

    public String getPassword() {

        //System.out.println("getPassword........");

        return password;
    }

    public void setPassword(String password) {
        //System.out.println("setPassword........");
        this.password = password;
    }

    public String getDbSource() {
        //System.out.println("getDbSource........");
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        //System.out.println("setDbSource........");
        this.dbSource = dbSource;
    }

    public Date getCreateTime() {
        //System.out.println("getCreateTime........");
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        //System.out.println("setCreateTime........");
        this.createTime = createTime;
    }
}
