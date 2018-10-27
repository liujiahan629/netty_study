package com.ljh.netty.bio.asynchronous;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            }catch (NumberFormatException e){

            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port : " +  port);
            Socket socket =null;
            //thread pool
            TimeServerHandlerExecutePool singleExecutePool = new TimeServerHandlerExecutePool(50,10000);
            while (true){
                socket =serverSocket.accept();
                singleExecutePool.execute(new TimeServerHandler(socket));
            }
        }finally {
            if(serverSocket != null){
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket =null;
            }
        }

    }
}
