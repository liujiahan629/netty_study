package com.ljh.netty.http_xml.server;

import com.ljh.netty.http_xml.codec.HttpXmlRequest;
import com.ljh.netty.http_xml.codec.HttpXmlResponse;
import com.ljh.netty.http_xml.pojo.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author liujiahan
 * @Title: HttpXmlServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
        HttpRequest request = msg.getRequest();
        Order order = (Order) msg.getBody();
        System.out.println("http serever receive request:" + order);
        dobusiness(order);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
        if (!HttpHeaderUtil.isKeepAlive(request)) {
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ctx.close();
                }
            });
        }
    }

    private void dobusiness(Order order) {
        order.getCustomer().setFirstName("server");
        order.getCustomer().setLastName("ljh-server");
        order.getBillTo().setCity("ljh-beijing");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("shibai:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
