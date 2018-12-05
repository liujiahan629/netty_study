package com.ljh.netty.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liujiahan
 * @Title: Reactor
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/12/3
 * @ModifiedBy:
 */
public class Reactor implements Runnable {
    Selector selector;

    public Reactor() throws IOException {
        selector = Selector.open();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //循环，等待事件
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()){
                    //调用handler，处理事件
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException ex) { /* ... */
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null){
            r.run();
        }
    }
}
