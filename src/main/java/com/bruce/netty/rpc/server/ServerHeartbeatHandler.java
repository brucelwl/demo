package com.bruce.netty.rpc.server;

import com.bruce.netty.rpc.client.ClientHeartbeatHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by bruce on 2021/1/10 17:07
 */
public class ServerHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(ClientHeartbeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            //该事件需要配合 io.netty.handler.timeout.IdleStateHandler使用
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                //超过指定时间没有读事件,关闭连接
                log.info("超过心跳时间,关闭和服务端的连接:{}", ctx.channel().remoteAddress());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
}
