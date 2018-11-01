package com.ljh.netty.protocol_stack.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiahan
 * @Title: Header
 * @Copyright: Copyright (c) 2018
 * @Description: Header实体
 * @Created on 2018/10/31
 * @ModifiedBy:
 */
public final class Header {
    private int crcCode = 0xabef0101;
    //消息长度
    private int length;
    //会话ID
    private long sessionID;
    //消息类型
    private byte type;
    //消息优先级
    private byte priority;

    //附件
    private Map<String,Object> attachment = new HashMap<String,Object>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
