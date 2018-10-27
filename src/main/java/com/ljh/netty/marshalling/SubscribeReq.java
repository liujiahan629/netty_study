package com.ljh.netty.marshalling;

import java.io.Serializable;

/**
 * @author liujiahan
 * @Title: SubscribeReq
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/26
 * @ModifiedBy:
 */
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = 3264274012377622215L;
    private Integer subReqId;
    private String userName;
    private String productName;
    private String address;

    public Integer getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(Integer subReqId) {
        this.subReqId = subReqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
