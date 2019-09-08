package com.bruce.netty.rpc.server.fix;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by bruce on 2019/9/7 20:27
 */
public class ServerHandlerCombination extends LoggingHandler {

    private SocketChannelRecvByteBufAllocatorHandler allocatorHandler;

    public ServerHandlerCombination(SocketChannelRecvByteBufAllocatorHandler allocatorHandler) {
        this.allocatorHandler = allocatorHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        allocatorHandler.channelRead(ctx, msg);

        super.channelRead(ctx, msg);

    }

}
