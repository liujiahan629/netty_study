package com.ljh.netty.netty01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liujiahan
 * @Title: TimeClient
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/23
 * @ModifiedBy:
 */
public class TimeClient {

    /**
     * connect server
     * @param port
     * @param host
     */
    public void connect(int port , String host){
        EventLoopGroup group =new NioEventLoopGroup();

        try {
            Bootstrap b  =new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new TimeClientHandler());
                }
            });
            //发起异步连接操作
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8090;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new TimeClient().connect(port,"127.0.0.1");
    }

}
