package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: Red
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("inside red fill() method");
    }
}
