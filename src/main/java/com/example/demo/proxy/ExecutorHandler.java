package com.example.demo.proxy;

import java.sql.SQLException;
import java.util.List;

public interface ExecutorHandler {

    int getProxyCount();

    int update(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter);

}
