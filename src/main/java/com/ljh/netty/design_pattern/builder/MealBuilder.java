package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: MealBuilder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class MealBuilder {

    public Meal prepareVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }


    public Meal prepareNonVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }

}
