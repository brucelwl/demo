package com.bruce.netty.rpc.client;


import com.bruce.netty.rpc.handler.codec.MarshallingCodeFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.net.ConnectException;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.TimeUnit;


public class NettyClient {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyClient.class);

    private EventLoopGroup workerGroup;
    private Bootstrap bootstrap;
    private volatile Channel clientChannel;

    public NettyClient(int threads) {

        workerGroup = new NioEventLoopGroup(threads);
        Class<? extends SocketChannel> socketChannelClass = NioSocketChannel.class;

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(socketChannelClass)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                .handler(new ClientHandlerInitializer(this));
    }

    public boolean connect() {
        log.info("尝试连接到服务端: 127.0.0.1:8088");
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8088);

            boolean notTimeout = channelFuture.awaitUninterruptibly(30, TimeUnit.SECONDS);
            clientChannel = channelFuture.channel();
            if (notTimeout) {
                if (clientChannel != null && clientChannel.isActive()) {
                    log.info("ZSKeeper client started !!! {} connect to server", clientChannel.localAddress());
                    return true;
                }
                Throwable cause = channelFuture.cause();
                if (cause != null) {
                    exceptionHandler(cause);
                }
            } else {
                log.warn("connect remote host[{}] timeout {}s", clientChannel.remoteAddress(), 30);
            }
            clientChannel.close();
            return false;
        } catch (Exception e) {
            exceptionHandler(e);
            clientChannel.close();
            return false;
        }
    }

    public void connectAsync() {
        log.info("尝试连接到服务端: 127.0.0.1:8088");
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8088);
        boolean notTimeout = channelFuture.awaitUninterruptibly(30, TimeUnit.SECONDS);
        channelFuture.addListener((ChannelFutureListener) future -> {
            Throwable cause = future.cause();
            if (cause != null) {
                exceptionHandler(cause);
                // ScheduledFuture<?> scheduledFuture = channelFuture.channel().eventLoop().schedule((Runnable) NettyClient.this::connectAsync, 2, TimeUnit.SECONDS);
                // while (!scheduledFuture.isDone()){
                //     scheduledFuture.await(20);
                // }
                // Throwable cause1 = scheduledFuture.cause();
                // if (cause1 != null){
                //     cause1.printStackTrace();
                // }
                future.channel().pipeline().fireChannelInactive();
            } else {
                clientChannel = channelFuture.channel();
                if (clientChannel != null && clientChannel.isActive()) {
                    log.info("ZSKeeper client started !!! {} connect to server", clientChannel.localAddress());
                }
            }
        });



    }


    private void exceptionHandler(Throwable cause) {
        if (cause instanceof ConnectException) {
            log.error("连接异常:{}", cause.getMessage());
        } else if (cause instanceof ClosedChannelException) {
            log.error("connect error:{}", "client has destroy");
        } else {
            log.error("connect error:", cause);
        }
    }

    public void close() {
        if (clientChannel != null) {
            clientChannel.close();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return clientChannel;
    }

    static class ClientHandlerInitializer extends ChannelInitializer<SocketChannel> {

        private NettyClient client;

        public ClientHandlerInitializer(NettyClient client) {
            this.client = client;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingDecoder());
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingEncoder());
            pipeline.addLast(new IdleStateHandler(25, 0, 10));
            pipeline.addLast(new ClientHeartbeatHandler());
            pipeline.addLast(new SimpleClientHandler(client));
        }
    }


}
