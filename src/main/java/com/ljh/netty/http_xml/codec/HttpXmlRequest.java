package com.ljh.netty.http_xml.codec;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author liujiahan
 * @Title: HttpXmlRequest
 * @Copyright: Copyright (c) 2018
 * @Description: 请求封装实体
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class HttpXmlRequest {

    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public final FullHttpRequest getRequest() {
        return request;
    }

    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }
}
