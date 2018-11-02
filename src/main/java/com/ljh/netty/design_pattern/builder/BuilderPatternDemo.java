package com.ljh.netty.design_pattern.builder;

/**
 * @author liujiahan
 * @Title: BuilderPatternDemo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/2
 * @ModifiedBy:
 */
public class BuilderPatternDemo {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();

        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("veg meal");
        vegMeal.showItems();
        System.out.println("total cost:" + vegMeal.getCost());


        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("non-veg meal");
        nonVegMeal.showItems();
        System.out.println("total cost:" + nonVegMeal.getCost());

    }
}
