package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: AbstractFactory
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public abstract class AbstractFactory {

    abstract Color getColor(String colorType);

    abstract Shape getShape(String shapeType);
}
