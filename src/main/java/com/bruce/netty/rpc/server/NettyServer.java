package com.bruce.netty.rpc.server;

import com.bruce.netty.rpc.handler.codec.MarshallingCodeFactory;
import com.bruce.util.PlatformUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @see AdaptiveRecvByteBufAllocator.HandleImpl#record
 *  if (actualReadBytes <= SIZE_TABLE[max(0, index - INDEX_DECREMENT - 1)])
 *
 * here shouldn't use --> index - INDEX_DECREMENT - 1
 * should use         --> index - INDEX_DECREMENT
 */
@Slf4j
public class NettyServer {

    static {
        System.setProperty("io.netty.leakDetection.level", "PARANOID");
    }

    public static void main(String[] args) throws Exception {

        boolean available = Epoll.isAvailable();
        log.info("Epoll:{}", available);

        EventLoopGroup acceptGroup = PlatformUtil.isLinux() ? new EpollEventLoopGroup(2) : new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = PlatformUtil.isLinux() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        Class<? extends ServerSocketChannel> serverSocketChannelClass = PlatformUtil.isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(acceptGroup, workerGroup)
                .channel(serverSocketChannelClass)
                //作用等同于 channel(serverSocketChannelClass)
                //.channelFactory(() -> PlatformUtil.isLinux() ? new EpollServerSocketChannel() : new NioServerSocketChannel())
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, true)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .attr(AttributeKey.valueOf("time"), LocalDateTime.now())
                .childAttr(AttributeKey.valueOf("time"), LocalDateTime.now())
                .childOption(ChannelOption.SO_KEEPALIVE, false) //默认为false
                .handler(new LoggingHandler())
                //.childHandler(new ServerHandlerChannelInitializer());
                .childHandler(new CustomCodecChannelInitializer());

        try {
            //sync() 同步等待连接,连接成功后继续执行
            ChannelFuture channelFuture = bootstrap.bind(8088).sync();
            //同步等待
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            acceptGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class CustomCodecChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingDecoder());
            pipeline.addLast(MarshallingCodeFactory.buildMarshallingEncoder());
            pipeline.addLast(new SimpleServerHandler());
        }
    }


}
