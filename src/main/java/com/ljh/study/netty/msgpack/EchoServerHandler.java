package com.ljh.study.netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: EchoServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/23
 * @ModifiedBy:
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    int counter = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //String body = (String) msg;
        System.out.println("This  is" + ++counter + "times receive client : [" + msg + "]");
        //body += "$_";
        //ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.write(msg);
    }
}
