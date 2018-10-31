package com.ljh.netty.protocol_stack.server;

import com.ljh.netty.protocol_stack.auth.LoginAuthRespHandler;
import com.ljh.netty.protocol_stack.codec.NettyMessageDecoder;
import com.ljh.netty.protocol_stack.codec.NettyMessageEncoder;
import com.ljh.netty.protocol_stack.heart_beat.HeartBeatRespHandler;
import com.ljh.netty.protocol_stack.pojo.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author liujiahan
 * @Title: NettyServer
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class NettyServer {


    public void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                ch.pipeline().addLast(new NettyMessageEncoder());
                ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                ch.pipeline().addLast(new LoginAuthRespHandler());
                ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
            }
        });

        //绑定端口，同步等待成功
        //b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        Channel ch =b.bind(NettyConstant.PORT).sync().channel();
        System.out.println("netty server start ok:" + (NettyConstant.REMOTEIP + ":" + NettyConstant.PORT));
        ch.closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().bind();
    }
}
