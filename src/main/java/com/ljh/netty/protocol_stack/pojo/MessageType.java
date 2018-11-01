package com.ljh.netty.protocol_stack.pojo;

/**
 * @author liujiahan
 * @Title: MessageType
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public enum  MessageType {
    /**
     * 0: 表示请求消息
     *
     * 1: 业务响应消息
     *
     * 2: 业务ONE WAY消息(即是请求又是响应消息)
     *
     * 3: 握手请求消息
     *
     * 4: 握手应答消息
     *
     * 5: 心跳请求消息
     *
     * 6: 心跳应答消息
     */
    REQ_CODE((byte)0),
    BUSINESS_REQ((byte)1),
    BUSINESS_RESP((byte)2),
    LOGIN_REQ((byte)3),
    LOGIN_RESP((byte)4),
    HEARTBEAT_REQ((byte)5),
    HEARTBEAT_RESP((byte)6);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }

    public static MessageType valueOf(byte type) {
        for (MessageType t : values()) {
            if (t.value == type) {
                return t;
            }
        }
        throw new IllegalArgumentException("unknown message type: " + type);
    }

}
