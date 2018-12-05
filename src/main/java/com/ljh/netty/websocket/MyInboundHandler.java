package com.ljh.netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: MyInboundHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/27
 * @ModifiedBy:
 */

/**
 * 拦截active时间，进行日志打印
 */
@ChannelHandler.Sharable
public class MyInboundHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("tcp connection");
        ctx.fireChannelActive();
    }

}
