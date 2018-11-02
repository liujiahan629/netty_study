package com.ljh.netty.design_pattern.factory;

/**
 * @author liujiahan
 * @Title: FactoryPatternDemo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.getShape("circle");
        shape1.draw();
        Shape shape2 =shapeFactory.getShape("square");
        shape2.draw();
    }
}
