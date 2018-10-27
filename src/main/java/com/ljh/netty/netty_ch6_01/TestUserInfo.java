package com.ljh.netty.netty_ch6_01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author liujiahan
 * @Title: TestUserInfo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/24
 * @ModifiedBy:
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(10).buildUserName("ljh study netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(userInfo);
        oos.flush();
        oos.close();
        byte[] bytes = bos.toByteArray();
        System.out.println("the jdk serializable length is" + bytes.length);
        bos.close();
        System.out.println("---------------------------------------------");
        System.out.println("the byte array serializable length is " + userInfo.codeC().length);
    }
}
