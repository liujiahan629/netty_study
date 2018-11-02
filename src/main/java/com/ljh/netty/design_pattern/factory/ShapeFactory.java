package com.ljh.netty.design_pattern.factory;

/**
 * @author liujiahan
 * @Title: ShapeFactory
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class ShapeFactory {


    public Shape getShape(String shapeType){
        if(shapeType ==null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("circle")){
            return new Circle();
        }
        if(shapeType.equalsIgnoreCase("square")){
            return new Square();
        }

        return null;
    }
}
