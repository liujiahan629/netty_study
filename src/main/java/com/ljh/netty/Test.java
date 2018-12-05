package com.ljh.netty;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

/**
 * @author liujiahan
 * @Title: Test
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/9
 * @ModifiedBy:
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        ExpressRunner runner = new ExpressRunner();
//        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
//        context.put("a",1);
//        context.put("b",2);
//        context.put("c",3);
//        String express = "a+b*c";
//        Object r = runner.execute(express, context, null, true, false);
//        System.out.println(r);
//        BigDecimal s = new BigDecimal(2);
//        System.out.println(Runtime.getRuntime().availableProcessors());

//        for(;;){
//            System.out.println("你好你好你好");
//            Thread.sleep(1000);
//        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("buffer = " + buffer);
    }

}
