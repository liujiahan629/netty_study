package com.ljh.netty.design_pattern.proxy_pattern;

import java.lang.reflect.Proxy;

/**
 * @author liujiahan
 * @Title: ProxyPatternDemo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class ProxyPatternDemo {

    public static void main(String[] args) {
        Image image = new ProxyImage("test_ljh.jpg");

        image.display();
        System.out.println("-------------------------");
        image.display();


        Image realImage = new RealImage("ljh-reflaction");
        Image proxyImage = (Image) Proxy.newProxyInstance(Image.class.getClassLoader(),new Class[]{Image.class},new DynamicProxyHandler(realImage));

        proxyImage.display();

    }
}
