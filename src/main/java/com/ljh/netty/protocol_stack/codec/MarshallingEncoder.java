package com.ljh.netty.protocol_stack.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author liujiahan
 * @Title: MarshallingEncoder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    /**
     * 使用marshall对Object进行编码，并且写入bytebuf
     * @param msg
     * @param out
     * @throws IOException
     */
    protected void encode(Object msg , ByteBuf out) throws IOException {
        try {
            //获取写入的位置
            int lengthPos = out.writerIndex();
            //2,先写入4个bytes，用于记录object对象编码后的长度
            out.writeBytes(LENGTH_PLACEHOLDER);
            //3，使用代理对象，防止marshaller写完后关闭bute buf
            ChannelBufferByteOutput output =new ChannelBufferByteOutput(out);
            //开始往butebuf中编码
            marshaller.start(output);
            marshaller.writeObject(msg);
            //编码长度
            marshaller.finish();
            //设置对象长度
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        }finally {
            marshaller.close();
        }
    }
}
