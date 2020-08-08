package com.example.demo.proxy;

public class MappedStatement {

    private String sqlId;
    private Integer fetchSize;
    private Integer timeout;
    private String resource;

    public MappedStatement(String sqlId, Integer fetchSize, Integer timeout, String resource) {
        this.sqlId = sqlId;
        this.fetchSize = fetchSize;
        this.timeout = timeout;
        this.resource = resource;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
