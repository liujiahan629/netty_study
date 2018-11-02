package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: Item
 * @Copyright: Copyright (c) 2018
 * @Description: 食品条目接口
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public interface Item {

     String name();
     Packing packing();
     float price();
}
