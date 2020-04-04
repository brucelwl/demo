package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bruce on 2020/3/26 12:14
 */
public class LogFactory {

    public static Logger getLog(Class<?> clazz){
        Logger logger = LoggerFactory.getLogger(clazz);
        ServerLogger serverLogger = new ServerLogger(logger);
        return serverLogger;
    }




}
