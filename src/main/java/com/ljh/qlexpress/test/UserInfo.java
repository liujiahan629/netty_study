package com.ljh.qlexpress.test;

/**
 * @author liujiahan
 * @Title: UserInfo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/16
 * @ModifiedBy:
 */
public class UserInfo {

    private long id;
    private long tag;
    private String name;


    public UserInfo(long id, String name,long tag) {
        this.id = id;
        this.tag = tag;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
