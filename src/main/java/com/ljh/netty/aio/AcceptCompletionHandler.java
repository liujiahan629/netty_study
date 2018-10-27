package com.ljh.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author liujiahan
 * @Title: AcceptCompletionHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/21
 * @ModifiedBy:
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

    /**
     * success handler
     * @param result
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment,this);
        ByteBuffer buffer =ByteBuffer.allocate(1024);

        //注意回调
        result.read(buffer,buffer,new ReadComplateHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}
