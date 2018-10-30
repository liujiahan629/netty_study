package com.ljh.netty.http_xml.server;


import com.ljh.netty.http_xml.codec.HttpXmlRequestDecoder;
import com.ljh.netty.http_xml.pojo.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * @author liujiahan
 * @Title: HttpXmlServer
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlServer {

    public void run(final int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //1,http请求消息解码器
                ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                //2，HttpObjectAggregator将多个消息转换为单一的FullHttpRequest或者FullHttpResponse
                ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                //xml解码
                ch.pipeline().addLast("xml-decoder",new HttpXmlRequestDecoder(Order.class));

                //3,http响应编码器
                ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                //xml编码
                ch.pipeline().addLast("xml-encoder",new HttpResponseEncoder());

                //业务处理
                ch.pipeline().addLast("xmlServerHandler", new HttpXmlServerHandler());
            }
        });
        ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
        System.out.println("http文件目录服务器启动，网址是：" + "http://localhost:" + port);
        future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}


    public static void main(String[] args) {
        int port = 8091;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new HttpXmlServer().run(port);

    }
}
