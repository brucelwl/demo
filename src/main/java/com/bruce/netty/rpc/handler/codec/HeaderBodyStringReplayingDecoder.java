package com.bruce.netty.rpc.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;


public class HeaderBodyStringReplayingDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int dataLen = in.readInt();
        ByteBuf buffer = ctx.alloc().heapBuffer(dataLen);
        try {
            in.readBytes(buffer);
            out.add(buffer.toString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(buffer);
        }
    }
}
