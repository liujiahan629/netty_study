package com.ljh.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author liujiahan
 * @Title: WebSocketServer
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/30
 * @ModifiedBy:
 */
public class WebSocketServer {


    public void run(final int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("http-codec",new HttpServerCodec());
                    pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
                    pipeline.addLast("http-chunked",new ChunkedWriteHandler());
                    //业务处理
                    ch.pipeline().addLast("handler", new WebSocketServerHandler());
                }
            });
            Channel ch =b.bind(port).sync().channel();
            System.out.println("web socket server started at port" + port + ".");
            System.out.println("open your browser and navigate to http://localhost:"  + port + "/");
            ch.closeFuture().sync();
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


        new WebSocketServer().run(port);

    }
}
