package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: Burger
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public abstract class Burger  implements Item{
    @Override
    public Packing packing() {
        return new Wrapper();
    }
}
