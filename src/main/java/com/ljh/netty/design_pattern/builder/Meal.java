package com.ljh.netty.design_pattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiahan
 * @Title: Meal
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class Meal {
    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item){
        this.items.add(item);
    }

    public float getCost(){
        float cost = 0.0f;
        for(Item item :items){
            cost += item.price();
        }
        return cost;
    }

    public void showItems(){
        for(Item item :items){
            System.out.println("item:" + item.name());
            System.out.println("packing: " + item.packing().pack() );
            System.out.println("price:" +item.price());
        }
    }
}
