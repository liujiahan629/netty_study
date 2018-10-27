package com.ljh.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: SubReqClientHandle
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/25
 * @ModifiedBy:
 */
public class SubReqClientHandle extends ChannelHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ;i<10 ;i++){
            ctx.write(subReq(1));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(i);
        builder.setUserName("ljh");
        builder.setProductName("netty-book");
        builder.setAddress("beijing");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive server response ：【" + msg + "】");
    }
}
