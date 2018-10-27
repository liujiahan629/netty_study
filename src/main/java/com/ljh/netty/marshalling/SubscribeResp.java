package com.ljh.netty.marshalling;

import java.io.Serializable;

/**
 * @author liujiahan
 * @Title: SubscribeResp
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/26
 * @ModifiedBy:
 */
public class SubscribeResp implements Serializable {

    private static final long serialVersionUID = 2332007834545858796L;
    private Integer subReqId;
    private Integer subRespCode;
    private String desc;

    public Integer getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(Integer subReqId) {
        this.subReqId = subReqId;
    }

    public Integer getSubRespCode() {
        return subRespCode;
    }

    public void setSubRespCode(Integer subRespCode) {
        this.subRespCode = subRespCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
