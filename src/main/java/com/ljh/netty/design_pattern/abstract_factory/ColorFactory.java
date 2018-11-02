package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: ColorFactory
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class ColorFactory extends AbstractFactory {
    @Override
    Color getColor(String colorType) {
        if(colorType == null){
            return null;
        }
        if(colorType.equalsIgnoreCase("RED")){
            return new Red();
        } else if(colorType.equalsIgnoreCase("GREEN")){
            return new Green();
        }
        return null;
    }

    @Override
    Shape getShape(String shapeType) {
        return null;
    }
}
