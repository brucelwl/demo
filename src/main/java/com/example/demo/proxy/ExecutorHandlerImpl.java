package com.example.demo.proxy;

import java.sql.SQLException;
import java.util.List;

public class ExecutorHandlerImpl implements ExecutorHandler {

    private int count;

    public ExecutorHandlerImpl(int count) {
        this.count = count;
    }

    @Override
    public int getProxyCount() {
        return count;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return 10 * 20 / 5 * 2 + 1 + 15;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        return null;
    }
}
