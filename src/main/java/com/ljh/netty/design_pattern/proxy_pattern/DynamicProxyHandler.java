package com.ljh.netty.design_pattern.proxy_pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liujiahan
 * @Title: DynamicProxyHandler
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object object;


    public DynamicProxyHandler(final Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("display准备");
        Object result = method.invoke(object,args);
        System.out.println("display结束");

        return result;
    }
}
