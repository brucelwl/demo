package com.bruce.netty.rpc.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded" + this.hashCode());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("server channelRead:{}", msg);

        ctx.channel().writeAndFlush("hello netty");
        // ctx.channel().close();

        ctx.channel().attr(AttributeKey.valueOf("name"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof java.io.IOException) {
            log.warn("client close");
        } else {
            cause.printStackTrace();
        }
    }
}
