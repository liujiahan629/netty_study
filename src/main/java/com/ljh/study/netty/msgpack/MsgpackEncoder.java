package com.ljh.study.netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author liujiahan
 * @Title: MsgpackEncoder
 * @Copyright: Copyright (c) 2018
 * @Description: 利用MessagePack编码 MessageToByteEncoder<I> extends ChannelHandlerAdapter
 * @Created on 2018/10/24
 * @ModifiedBy:
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack =new MessagePack();
        //Serialize
        byte[] raw = messagePack.write(o);
        byteBuf.writeBytes(raw);

    }
}
