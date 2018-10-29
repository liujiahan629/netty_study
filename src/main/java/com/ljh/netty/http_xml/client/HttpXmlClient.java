package com.ljh.netty.http_xml.client;

import com.ljh.netty.http_xml.codec.HttpXmlRequestEncoder;
import com.ljh.netty.http_xml.codec.HttpXmlResponseDecoder;
import com.ljh.netty.http_xml.pojo.Order;
import com.ljh.netty.marshalling.MarshallingCodeFactory;
import com.ljh.netty.marshalling.SubReqClient;
import com.ljh.netty.marshalling.SubReqClientHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * @author liujiahan
 * @Title: HttpXmlClient
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlClient {

    public void connect(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //1,http请求消息解码器
                    ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                    //2，HttpObjectAggregator将多个消息转换为单一的FullHttpRequest或者FullHttpResponse
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                    //xml解码器
                    ch.pipeline().addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class,true));
                    //3,http响应编码器
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    //xml编码器
                    ch.pipeline().addLast("xml-encoder",new HttpXmlRequestEncoder());
                    //业务处理
                    ch.pipeline().addLast("xmlClientHandler",new HttpXmlClientHandler());
                }
            });
            ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port =8092;
        if(args !=null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        new HttpXmlClient().connect(port);
    }
}
