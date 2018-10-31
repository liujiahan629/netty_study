package com.ljh.netty.protocol_stack.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author liujiahan
 * @Title: MarshallingDecoder
 * @Copyright: Copyright (c) 2018
 * @Description: 其实netty也提供了，解码的工具类
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }


    protected Object decode(ByteBuf in) throws IOException, ClassNotFoundException {
        //1,读取第一个4bytes,里边放置的object对象的byte长度
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(),objectSize);
        //2，使用byteBuf的代理类
        ByteInput input = new ChannelBufferByteInput(buf);

        try {
            //3开始解码
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            //读完之后设置读取的位置
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        }finally {
            unmarshaller.close();
        }
    }
}
