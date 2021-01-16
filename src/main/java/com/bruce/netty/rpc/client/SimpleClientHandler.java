package com.bruce.netty.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(SimpleClientHandler.class);

    private NettyClient client;

    public SimpleClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client receive:{}", msg);
    }


    /** 客户端正常下线时执行该方法 */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("channelInactive:{}", ctx.channel().localAddress());

        reconnection(ctx);

        //reconnectionAsync(ctx);
    }

    /** 入栈发生异常时执行exceptionCaught */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            log.warn("exceptionCaught:客户端[{}]和远程断开连接", ctx.channel().localAddress());
        } else {
            log.error(cause);
        }

        reconnection(ctx);
    }


    private void reconnection(ChannelHandlerContext ctx) {
        log.info("5s之后重新建立连接");
        // ScheduledFuture<?> scheduledFuture = ctx.channel().eventLoop().schedule(new Runnable() {
        //     @Override
        //     public void run() {
        //         boolean connect = client.connect();
        //         if (connect) {
        //             log.info("重新连接成功");
        //         } else {
        //             reconnection(ctx);
        //         }
        //     }
        // }, 3, TimeUnit.SECONDS);
        //
        // scheduledFuture.addListener(new GenericFutureListener() {
        //     @Override
        //     public void operationComplete(Future future) throws Exception {
        //         Throwable cause = future.cause();
        //         if (cause != null) {
        //             cause.printStackTrace();
        //         }
        //     }
        // });

    }

    private void reconnectionAsync(ChannelHandlerContext ctx) {
        log.info("5s之后重新建立连接");
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                client.connectAsync();
            }
        }, 5, TimeUnit.SECONDS);

    }


}
