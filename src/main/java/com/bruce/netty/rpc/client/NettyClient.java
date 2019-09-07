package com.bruce.netty.rpc.client;


import com.bruce.netty.rpc.handler.codec.HeaderBodyStringDecoder;
import com.bruce.netty.rpc.handler.codec.HeaderBodyStringEncoder;
import com.bruce.util.PlatformUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

public class NettyClient {
    static {
        System.setProperty("io.netty.leakDetection.level", "PARANOID");
    }

    public static void main(String[] args) {

        EventLoopGroup workerGroup = PlatformUtil.isLinux() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        Class<? extends SocketChannel> socketChannelClass = PlatformUtil.isLinux() ? EpollSocketChannel.class : NioSocketChannel.class;

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(socketChannelClass)
                //.option(, )
                //.handler(new ClientHandlerChannelInitializer());
                .handler(new CustomCodecChannelInitializer());

        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8088).sync();

            for (int i = 0; i < 200; i++) {
                Thread.sleep(3000);
                //连接成功后发送数据
                channelFuture.channel().writeAndFlush("随机数:" + i + " " + UUID.randomUUID().toString()
                        +"随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机数随机" +
                        "数随机数随机数随机数随机数随机数数随机数随机数随机数随机数随机数数随机数随机数随机数随机数随机数数随机数随机");
            }

            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


    static class CustomCodecChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HeaderBodyStringDecoder());
            pipeline.addLast(new HeaderBodyStringEncoder());
            pipeline.addLast(new SimpleClientHandler());
        }
    }




}
