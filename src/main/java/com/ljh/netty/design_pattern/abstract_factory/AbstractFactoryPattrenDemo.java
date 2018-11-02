package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: AbstractFactoryPattrenDemo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class AbstractFactoryPattrenDemo {

    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        Shape shape1 = shapeFactory.getShape("circle");
        shape1.draw();

        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        Color color =colorFactory.getColor("red");
        color.fill();
    }
}
