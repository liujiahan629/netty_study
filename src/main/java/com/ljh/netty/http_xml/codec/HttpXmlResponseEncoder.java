package com.ljh.netty.http_xml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * @author liujiahan
 * @Title: HttpXmlResponseEncoder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    /**
     * 利用父类的encode0进行编码
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {

        ByteBuf body = encode0(ctx, msg.getResult());
        FullHttpResponse response = msg.getHttpResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(), msg.getHttpResponse().status(), body);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
        HttpHeaderUtil.setContentLength(response, body.readableBytes());
        out.add(response);
    }
}
