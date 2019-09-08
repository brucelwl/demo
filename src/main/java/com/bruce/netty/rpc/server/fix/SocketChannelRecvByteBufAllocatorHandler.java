package com.bruce.netty.rpc.server.fix;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 不要调用 ctx.fireChannelRead(msg);
 * <p>
 * Created by bruce on 2019/9/7 20:33
 */
public class SocketChannelRecvByteBufAllocatorHandler {

    @SuppressWarnings("all")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg.getClass().getName());

        if (msg instanceof NioSocketChannel) {
            NioSocketChannel nioSocketChannel = (NioSocketChannel) msg;

            Field configField = ReflectionUtils.findField(NioSocketChannel.class, "config");
            ReflectionUtils.makeAccessible(configField);

            SocketChannelConfig config = (SocketChannelConfig) ReflectionUtils.getField(configField, nioSocketChannel);
            config.setRecvByteBufAllocator(new MyAdaptiveRecvByteBufAllocator());
        }
    }
}
