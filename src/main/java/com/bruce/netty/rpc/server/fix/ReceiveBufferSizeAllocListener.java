package com.bruce.netty.rpc.server.fix;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @see AdaptiveRecvByteBufAllocator.HandleImpl#record
 *
 * Created by bruce on 2019/6/22 19:53
 */
@Slf4j
public class ReceiveBufferSizeAllocListener extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        log.info("decode readableBytes:{},capacity:{},ByteBuf:{}", in.readableBytes(), in.capacity(), in);

        ctx.fireChannelRead(msg);
    }
}
