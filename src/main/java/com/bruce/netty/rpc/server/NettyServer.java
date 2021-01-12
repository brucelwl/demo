package com.bruce.netty.rpc.server;

import com.bruce.netty.rpc.handler.codec.MarshallingCodeFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @see AdaptiveRecvByteBufAllocator.HandleImpl#record
 * if (actualReadBytes <= SIZE_TABLE[max(0, index - INDEX_DECREMENT - 1)])
 * <p>
 * here shouldn't use --> index - INDEX_DECREMENT - 1
 * should use         --> index - INDEX_DECREMENT
 */
public class NettyServer {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyServer.class);

    // static {
    //     System.setProperty("io.netty.leakDetection.level", "PARANOID");
    // }

    public static void main(String[] args) throws Exception {

        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Class<? extends ServerSocketChannel> serverSocketChannelClass = NioServerSocketChannel.class;
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(acceptGroup, workerGroup)
                .channel(serverSocketChannelClass)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, false) //默认为false
                .handler(new LoggingHandler())
                .childHandler(new ChildHandlerInitializer());

        try {
            //sync() 同步等待连接,连接成功后继续执行
            ChannelFuture channelFuture = bootstrap.bind(8088).sync();
            log.info("server 启动成功");
            //同步等待
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("执行finally");
        }

        Thread serverShutdown = new Thread(() -> {
            log.info("执行jvm ShutdownHook, server shutdown");
            acceptGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });

        //注册jvm ShutdownHook
        //Runtime.getRuntime().addShutdownHook(serverShutdown);

    }

    static class ChildHandlerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingDecoder());
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingEncoder());
            pipeline.addLast(new IdleStateHandler(60, 0, 0));
            pipeline.addLast(new ServerHeartbeatHandler());
            pipeline.addLast(new SimpleServerHandler());
        }
    }


}
