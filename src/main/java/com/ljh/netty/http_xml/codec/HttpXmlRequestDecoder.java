package com.ljh.netty.http_xml.codec;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author liujiahan
 * @Title: HttpXmlRequestDecoder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {


    public HttpXmlRequestDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    protected HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    public void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        if (!msg.decoderResult().isSuccess()) {
            sendError(ctx,HttpResponseStatus.BAD_REQUEST);
            return;
        }

        HttpXmlRequest request = new HttpXmlRequest(msg, decode0(ctx, msg.content()));
        out.add(request);
    }


    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
