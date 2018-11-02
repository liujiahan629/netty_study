package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: Bottle
 * @Copyright: Copyright (c) 2018
 * @Description: 瓶子包装类
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Bottle implements Packing {

    @Override
    public String pack() {
        return "Bottle";
    }
}
