package com.bruce.netty.rpc.server.fix;

import com.bruce.netty.rpc.handler.codec.HeaderBodyStringEncoder;
import com.bruce.netty.rpc.handler.codec.HeaderBodyStringReplayingDecoder;
import com.bruce.netty.rpc.server.SimpleServerHandler;
import com.bruce.util.PlatformUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.bootstrap.ServerBootstrapConfig;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Slf4j
public class NettyServer2 {

    static {
        System.setProperty("io.netty.leakDetection.level", "PARANOID");
    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {

        EventLoopGroup acceptGroup = PlatformUtil.isLinux() ? new EpollEventLoopGroup(2) : new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = PlatformUtil.isLinux() ? new EpollEventLoopGroup() : new NioEventLoopGroup();


        ChannelFactory<ServerChannel> serverSocketChannelFactory = () -> {
            ServerSocketChannel serverSocketChannel;
            if (PlatformUtil.isLinux()) {
                serverSocketChannel = new EpollServerSocketChannel();
            } else {
                serverSocketChannel = new NioServerSocketChannel();
            }
            Field configField = ReflectionUtils.findField(serverSocketChannel.getClass(), "config");
            ReflectionUtils.makeAccessible(configField);
            ServerSocketChannelConfig channelConfig = (ServerSocketChannelConfig) ReflectionUtils.getField(configField, serverSocketChannel);
            channelConfig.setRecvByteBufAllocator(new MyAdaptiveRecvByteBufAllocator());
            return serverSocketChannel;
        };

        ServerHandlerCombination serverHandlerCombination = new ServerHandlerCombination(new SocketChannelRecvByteBufAllocatorHandler());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(acceptGroup, workerGroup)
                //.channel(serverSocketChannelClass)
                //作用等同于 channel(serverSocketChannelClass)
                .channelFactory(serverSocketChannelFactory)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, true)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) //连接超时
                .attr(AttributeKey.valueOf("time"), LocalDateTime.now())
                .childAttr(AttributeKey.valueOf("time"), LocalDateTime.now())
                .childOption(ChannelOption.SO_KEEPALIVE, false) //默认为false
                .handler(serverHandlerCombination)
                //.childHandler(new ServerHandlerChannelInitializer());
                .childHandler(new CustomCodecChannelInitializer());

        ServerBootstrapConfig config = bootstrap.config();


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
            pipeline.addLast(new ReceiveBufferSizeAllocListener());
            pipeline.addLast(new HeaderBodyStringReplayingDecoder());
            pipeline.addLast(new HeaderBodyStringEncoder());
            pipeline.addLast(new SimpleServerHandler());
        }
    }


}
