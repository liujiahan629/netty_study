package com.ljh.netty.nio;

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

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
