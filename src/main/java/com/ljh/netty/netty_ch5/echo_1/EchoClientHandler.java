package com.ljh.netty.netty_ch5.echo_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: EchoClientHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/23
 * @ModifiedBy:
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private int counter;
    static final String ECHO_REQ = "HI,LIUJIAHAN,WELCOME TO STUDY NETTY.$_";

    public EchoClientHandler() {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is " + ++counter + "times  receive server:[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
