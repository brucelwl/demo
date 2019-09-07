package com.bruce.netty.rpc.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by bruce on 2019/6/22 19:53
 */
@Slf4j
public class HeaderBodyStringDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("decode readableBytes:{},capacity:{},ByteBuf:{}", in.readableBytes(), in.capacity(), in);
        Object obj = null;
        do {
            obj = decode0(ctx, in);
            if (obj != null) {
                out.add(obj);
            }
        } while (obj != null);
    }

    private Object decode0(ChannelHandlerContext ctx, ByteBuf in) {
        Object obj = null;
        if (in.readableBytes() >= 4) {
            //int dataLen = in.readInt();
            int dataLen = in.getInt(in.readerIndex());
            if (in.readableBytes() >= dataLen + 4) {
                in.readerIndex(in.readerIndex() + 4);
                //非池化的堆上内存Java虚拟机会自动释放
                //ByteBuf buffer = Unpooled.buffer(dataLen);
                //池化的内存需要手动释放
                //ByteBuf buffer = Unpooled.directBuffer(dataLen);
                //ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(dataLen);
                ByteBuf buffer = ctx.alloc().heapBuffer(dataLen);
                try {
                    in.readBytes(buffer);
                    obj = buffer.toString(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ReferenceCountUtil.release(buffer);
                }
            }
        }
        return obj;
    }


}
