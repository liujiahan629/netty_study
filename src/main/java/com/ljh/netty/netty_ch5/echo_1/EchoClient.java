package com.ljh.netty.netty_ch5.echo_1;

import com.ljh.study.netty.msgpack.MsgpackDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author liujiahan
 * @Title: EchoClient
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/23
 * @ModifiedBy:
 */
public class EchoClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //几种编码器
                    //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                    //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                    socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(20));

                    //socketChannel.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
                    socketChannel.pipeline().addLast(new StringDecoder());
                    socketChannel.pipeline().addLast(new EchoClientHandler());
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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
        new EchoClient().connect(port, "127.0.0.1");
    }
}
