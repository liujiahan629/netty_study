package com.ljh.netty.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author liujiahan
 * @Title: Handler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/12/3
 * @ModifiedBy:
 */
public class Handler implements Runnable {

    //todo:init of socket
    final SocketChannel socket = null;
    final SelectionKey sk;

    Handler(Selector sel) throws IOException {
        //注册handler到Selector
        socket.configureBlocking(false);
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    @Override
    public void run() {
        System.out.println("handler handler");
    }
}
