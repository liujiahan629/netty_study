package com.ljh.netty.design_pattern.proxy_pattern;

/**
 * @author liujiahan
 * @Title: CglibProxyTest
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        Image realImage = new RealImage("ljh-cgl");
        CglibProxy cglibProxy = new CglibProxy();
        RealImage realImageProxy = (RealImage)cglibProxy.getInstance(realImage);
        realImageProxy.display();
    }
}
