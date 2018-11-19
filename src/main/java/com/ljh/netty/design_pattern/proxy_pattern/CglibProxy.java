package com.ljh.netty.design_pattern.proxy_pattern;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liujiahan
 * @Title: CglibProxy
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class CglibProxy  implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target){
        this.target = target;
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("display cglib 的准备");

        Object result = method.invoke(o,objects);
        System.out.println("display cglib 的结束");
        return result;

    }
}
