package com.ljh.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author liujiahan
 * @Title: WebSocketServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/30
 * @ModifiedBy:
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());

    private WebSocketServerHandshaker handshaker;

    /**
     * 接受到消息处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            //传统的http接入
            handlerHttpRequest(ctx,(FullHttpRequest)msg);
        }else if(msg instanceof WebSocketFrame){
            //WebSocket接入
            handlerWebSocketFrame(ctx,(WebSocketFrame)msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    /**
     * 处理正常请求
     * @param ctx
     * @param req
     */
    private void handlerHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        //如果HTTP解码失败，返回http异常
        if(!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
            return;
        }

        //构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8091/websocket",null,false);
        handshaker = wsFactory.newHandshaker(req);
        if(handshaker ==null){
            WebSocketServerHandshakerFactory .sendUnsupportedVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(),req);
        }
    }


    /**
     * 处理websocket请求
     * @param ctx
     * @param frame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        //判断是否是关闭链路的指令
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)frame.retain());
            return;
        }
        //判断是否为ping消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //本例仅支持文本消息，不支持二进制信息
        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("$s frame types not supported",frame.getClass().getName()));
        }

        //返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        if(logger.isLoggable(Level.FINE)){
            logger.fine(String.format("%s received %s",ctx.channel(),request));
        }
        ctx.channel().write(new TextWebSocketFrame(request + " ,欢迎使用netty websocket服务，现在时刻：" + new Date().toString()));
    }

    /**
     * 响应客户端消息
     * @param ctx
     * @param req
     * @param res
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx , FullHttpRequest req, FullHttpResponse res){
        if(res.status().code() !=200){
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(res,res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if(!HttpHeaderUtil.isKeepAlive(req) || res.status().code()!=200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
