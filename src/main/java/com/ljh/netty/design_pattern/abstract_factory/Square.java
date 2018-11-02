package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: Square
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("inside square:draw() method");
    }
}
