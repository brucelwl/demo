package com.bruce.netty.rpc.client;

import com.bruce.netty.rpc.entity.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2021/1/12 9:47
 */
public class SendTask implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(SendTask.class);

    private NettyClient client;
    private ScheduledExecutorService scheduledExecutor;

    public SendTask(NettyClient client, ScheduledExecutorService scheduledExecutor) {
        this.client = client;
        this.scheduledExecutor = scheduledExecutor;
    }

    @Override
    public void run() {
        try {
            Channel channel = client.getChannel();
            if (channel != null && channel.isActive()) {
                log.info("客户端发送数据");
                UserInfo userInfo = new UserInfo("bruce", 18);
                ChannelFuture channelFuture = channel.writeAndFlush(userInfo);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    Throwable cause = future.cause();
                    if (cause != null) {
                        log.error("发送失败:", cause);
                    }
                    if (future.isSuccess()) {
                        log.info("用户信息发送成功");
                    }
                });
            } else {
                if (channel == null) {
                    //log.warn("客户端未连接");
                } else {
                    log.warn("客户端已断开:{}", channel.localAddress());
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        //下一次定时任务
        long delay = ThreadLocalRandom.current().nextLong(30) + 2;

        //log.info("延时{}s之后再次发送", delay);

        scheduledExecutor.schedule(this, delay, TimeUnit.SECONDS);
    }
}
