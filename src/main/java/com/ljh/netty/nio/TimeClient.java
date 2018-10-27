package com.ljh.netty.nio;

/**
 * @author liujiahan
 * @Title: TimeClient
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/19
 * @ModifiedBy:
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8090;
        if(args !=null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }

        new Thread(new TimeClientHandler("127.0.0.1",port),"Timer-Client-001").start();

    }
}
