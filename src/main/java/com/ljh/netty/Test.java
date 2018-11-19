package com.ljh.netty;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

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
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a",1);
        context.put("b",2);
        context.put("c",3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }
}
