package com.bruce.netty.rpc.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * ByteToMessageDecoder 的子类不能是共享的,不能使用@Sharable注解
 * Created by bruce on 2019/6/22 19:53
 */
@Slf4j
public class PrintReadDataInfoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        log.info("decode readableBytes:{},capacity:{},ByteBuf:{}", in.readableBytes(), in.capacity(), in);

        ctx.fireChannelRead(msg);
    }
}
