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
