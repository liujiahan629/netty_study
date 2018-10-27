package com.ljh.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;


/**
 * @author liujiahan
 * @Title: TestSubscribeReqProto
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/25
 * @ModifiedBy:
 */
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("ljh");
        builder.setProductName("netty study");
        builder.setAddress("hebei shijiazhuang");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("before encode:" + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("after encode:" + req.toString());
        System.out.println("assert equal:——》" + req2.equals(req));
        System.out.println("encode : " + encode(req));
    }
}
