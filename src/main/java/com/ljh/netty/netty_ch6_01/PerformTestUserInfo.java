package com.ljh.netty.netty_ch6_01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author liujiahan
 * @Title: PerformTestUserInfo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/24
 * @ModifiedBy:
 */
public class PerformTestUserInfo {
    public static void main(String[] args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(10).buildUserName("ljh study netty");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(userInfo);
            oos.flush();
            oos.close();
            byte[] bytes = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("the jdk serializable cost time is " + (endTime - startTime) + "ms");
        System.out.println("---------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = userInfo.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("the byte array serializable cost time is " + (endTime - startTime) + "ms");
    }
}
