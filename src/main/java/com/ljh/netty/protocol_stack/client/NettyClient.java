package com.ljh.netty.protocol_stack.client;

import com.ljh.netty.protocol_stack.auth.LoginAuthReqHandler;
import com.ljh.netty.protocol_stack.codec.NettyMessageDecoder;
import com.ljh.netty.protocol_stack.codec.NettyMessageEncoder;
import com.ljh.netty.protocol_stack.heart_beat.HeartBeatReqHandler;
import com.ljh.netty.protocol_stack.pojo.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liujiahan
 * @Title: NettyClient
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public class NettyClient {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    EventLoopGroup group =new NioEventLoopGroup();

    public void connnet(int port,String host) throws InterruptedException {
        try {
            Bootstrap bootstrap =new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                    ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
                    ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                    ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
                    ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());
                }
            });

            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,port),new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        }finally {
            //释放完资源完成之后哦，清空资源，再次发起重新连接操作
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connnet(NettyConstant.PORT,NettyConstant.REMOTEIP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connnet(NettyConstant.PORT,NettyConstant.REMOTEIP);
    }

}
