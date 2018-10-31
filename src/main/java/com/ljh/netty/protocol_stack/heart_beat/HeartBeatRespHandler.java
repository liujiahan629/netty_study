package com.ljh.netty.protocol_stack.heart_beat;

import com.ljh.netty.protocol_stack.pojo.Header;
import com.ljh.netty.protocol_stack.pojo.MessageType;
import com.ljh.netty.protocol_stack.pojo.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiahan
 * @Title: HeartBeatRespHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;

        //返回心跳应答消息
        if(nettyMessage.getHeader() !=null && nettyMessage.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("receive client heart beat message:" + nettyMessage);

            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("send heart beat response message to client : " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else{
            ctx.fireChannelRead(msg);
        }
    }



    private NettyMessage buildHeartBeat(){
        NettyMessage nettyMessage =new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
