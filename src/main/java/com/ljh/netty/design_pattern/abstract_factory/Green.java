package com.ljh.netty.design_pattern.abstract_factory;

/**
 * @author liujiahan
 * @Title: Green
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("inside green fill() method");
    }
}
