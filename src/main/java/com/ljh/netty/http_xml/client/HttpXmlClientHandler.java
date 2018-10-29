package com.ljh.netty.http_xml.client;

import com.ljh.netty.http_xml.codec.HttpXmlRequest;
import com.ljh.netty.http_xml.codec.HttpXmlResponse;
import com.ljh.netty.http_xml.test.OrderFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liujiahan
 * @Title: HttpXmlClientHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("the client receive response of http header is: " +msg.getHttpResponse().headers().names());
        System.out.println("hte client receive response of http body is : " + msg.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }
}
