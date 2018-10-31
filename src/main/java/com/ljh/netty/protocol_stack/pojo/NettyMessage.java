package com.ljh.netty.protocol_stack.pojo;

/**
 * @author liujiahan
 * @Title: NettyMessage
 * @Copyright: Copyright (c) 2018
 * @Description: Netty消息类
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public final class NettyMessage {
    //消息头
    private Header header;
    //消息体
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
