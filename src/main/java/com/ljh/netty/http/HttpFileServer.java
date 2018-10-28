package com.ljh.netty.http;

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
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author liujiahan
 * @Title: HttpFileServer
 * @Copyright: Copyright (c) 2018
 * @Description: 基于Netty开发的HTTP Server端
 * @Created on 2018/10/27
 * @ModifiedBy:
 */
public class HttpFileServer {


    private static final String DEFAULT_URL = "/src/main/java/com/ljh/netty/";

    /**
     * 入口方法，Netty编写服务端的基本流程
     * @param port 端口号
     * @param url 访问地址
     */
    public void run(final int port, final String url) {
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
                    //3,http响应编码器
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    //4,ChunkedWriteHandler 支持异步发送大的码流
                    ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                    //5,业务处理
                    ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                }
            });
            ChannelFuture future = b.bind("localhost", port).sync();
            System.out.println("http文件目录服务器启动，网址是：" + "http://localhost:" + port + url);
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

        String url = DEFAULT_URL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileServer().run(port, url);

    }

}
