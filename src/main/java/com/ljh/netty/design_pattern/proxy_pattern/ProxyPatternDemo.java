package com.ljh.netty.design_pattern.proxy_pattern;

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
    }
}
