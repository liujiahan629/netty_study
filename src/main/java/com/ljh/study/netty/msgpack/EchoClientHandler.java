package com.ljh.study.netty.msgpack;

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
        UserInfo[] infos =UserInfo();
        for (UserInfo userInfo :infos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    private UserInfo[] UserInfo(){
        UserInfo[] userInfos =new UserInfo[100];
        UserInfo userInfo =null;
        for (int i = 0; i < 100; i++) {
            userInfo = new UserInfo();
            userInfo.setUserId(i);
            userInfo.setUserName("ljh" + i);
            userInfos[i]=userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is " + ++counter + "times  receive server:[" + msg + "]");
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
