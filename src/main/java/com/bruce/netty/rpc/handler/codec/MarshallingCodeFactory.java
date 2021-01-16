package com.bruce.netty.rpc.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.*;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * <pre>
 * netty提供的Marshalling编解码器采用消息头和消息体的方式
 *
 * JBoss Marshalling是一个Java对象序列化包,对jdk默认的序列化框架进行优化
 * 但又保持跟Serializable接口的兼容,同时增加了一些可调用的参数和附加的特性
 * 经过测试发现序列化后的流较protostuff,MessagePack还是比较大的,
 * 序列化和反序列化的类必须是同一个类,否则抛出异常:
 * io.netty.handler.codec.DecoderException: java.lang.ClassNotFoundException: com.bruce.netty.rpc.entity.UserInfo
 *
 * maven 依赖: jboss-marshalling-serial
 * </pre>
 *
 * @author liwenlong - 2018/3/9 16:05
 */
public final class MarshallingCodeFactory {

    /** 创建Jboss marshalling 解码器 */
    public static MarshallingDecoder buildMarshallingDecoder() {
        //参数serial表示创建的是Java序列化工厂对象,由jboss-marshalling-serial提供
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultUnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, configuration);

        return new MarshallingDecoder(provider, 1024);
    }

    /** 创建Jboss marshalling 编码器 */
    public static MarshallingEncoder buildMarshallingEncoder() {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultMarshallerProvider provider = new DefaultMarshallerProvider(factory, configuration);
        return new MarshallingEncoder(provider);
    }

}
