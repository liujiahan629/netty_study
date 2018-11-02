package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: Wrapper
 * @Copyright: Copyright (c) 2018
 * @Description: 纸包装类
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
