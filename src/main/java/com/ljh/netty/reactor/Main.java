package com.ljh.netty.reactor;

import java.io.IOException;

/**
 * @author liujiahan
 * @Title: Main
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/12/3
 * @ModifiedBy:
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //定义一个Reactor
        Reactor reactor = new Reactor();
        //Handler
        new Handler(reactor.selector);
        reactor.run();
    }
}
