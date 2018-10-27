package com.ljh.netty.aio;

import java.io.IOException;

/**
 * @author liujiahan
 * @Title: TimeServer
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/19
 * @ModifiedBy:
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8090;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        AsyncTimeServerHandler asyncTimeServerHandler =new AsyncTimeServerHandler(port);
        new Thread(asyncTimeServerHandler,"AIO-asyncTimeServerHandler-001").start();

    }
}
