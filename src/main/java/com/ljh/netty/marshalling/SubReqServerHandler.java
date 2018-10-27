package com.ljh.netty.marshalling;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: SubReqServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/25
 * @ModifiedBy:
 */
@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("ljh".equalsIgnoreCase(req.getUserName())) {
            System.out.println("server accept client subscribe req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqId()));
        }
    }

    private SubscribeResp resp(int subReqId) {
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setSubReqId(subReqId);
        subscribeResp.setSubRespCode(0);
        subscribeResp.setDesc("netty book is very good");
        return subscribeResp;

    }
}
