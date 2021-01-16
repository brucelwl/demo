package com.bruce.netty.rpc.client;


import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NettyClientMain2 {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyClientMain2.class);

    private static final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient(1);
        nettyClient.connectAsync();

        send(nettyClient);
    }

    /**
     * 定时发送数据
     */
    static void send(NettyClient client) {
        scheduledExecutor.schedule(new SendTask(client, scheduledExecutor), 2, TimeUnit.SECONDS);
    }


}
