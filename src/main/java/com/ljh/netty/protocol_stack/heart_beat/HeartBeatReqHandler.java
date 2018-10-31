package com.ljh.netty.protocol_stack.heart_beat;

import com.ljh.netty.protocol_stack.pojo.Header;
import com.ljh.netty.protocol_stack.pojo.MessageType;
import com.ljh.netty.protocol_stack.pojo.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liujiahan
 * @Title: HeartBeatReqHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        //握手成功，主动发送心跳信息
        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
        } else if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("client receive server heart beat message:" + nettyMessage);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }


    /**
     * 内部类：HeartBeatTask
     */
    private class HeartBeatTask implements Runnable {

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage nettyMessage = buildHeartBeat();
            System.out.println("client send heart beat message to server :——》" + heartBeat);
            ctx.writeAndFlush(heartBeat);

        }


        private NettyMessage buildHeartBeat() {
            NettyMessage nettyMessage = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            nettyMessage.setHeader(header);
            return nettyMessage;
        }
    }

}
