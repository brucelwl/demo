package com.bruce.netty.rpc.client;


import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NettyClientMain {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyClientMain.class);

    private static final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();

        boolean connect = false;

        //刚启动时尝试连接10次,都无法建立连接则不在尝试
        //如果想在刚启动后,一直尝试连接,需要放在线程中,异步执行,防止阻塞程序
        for (int i = 0; i < 10; i++) {
            connect = nettyClient.connect();
            if (connect) {
                break;
            }
            //连接不成功,隔5s之后重新尝试连接
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (connect) {
            log.info("定时发送数据");
            send(nettyClient);
        } else {
            nettyClient.close();
            log.info("进程退出");
        }
    }

    /**
     * 定时发送数据
     */
    static void send(NettyClient client) {
        scheduledExecutor.schedule(new SendTask(client,scheduledExecutor), 2, TimeUnit.SECONDS);
    }




}
