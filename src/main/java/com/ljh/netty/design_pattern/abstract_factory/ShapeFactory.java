package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: ShapeFactory
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class ShapeFactory extends AbstractFactory {

    @Override
    Color getColor(String colorType) {
        return null;
    }

    @Override
    Shape getShape(String shapeType) {
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        }  else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }
}
