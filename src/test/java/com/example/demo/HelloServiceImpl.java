package com.example.demo;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by bruce on 2019/12/19 13:29
 */
public class HelloServiceImpl {

    public void sayHello() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("hello:" + name);

        HashMap<String, DataSource> dataSourceMap = new HashMap<>();
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        Properties properties = new Properties();

        try {
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, properties);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
