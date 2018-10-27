package com.ljh.study.netty.msgpack;

/**
 * @author liujiahan
 * @Title: UserInfo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/24
 * @ModifiedBy:
 */
public class UserInfo  {
    private String userName;
    private int userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
