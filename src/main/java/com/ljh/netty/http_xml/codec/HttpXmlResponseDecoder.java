package com.ljh.netty.http_xml.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * @author liujiahan
 * @Title: HttpXmlResponseDecoder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
        HttpXmlResponse resHttpXmlResponse = new HttpXmlResponse(msg,decode0(ctx,msg.content()));
        out.add(resHttpXmlResponse);
    }
}
