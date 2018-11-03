package com.ljh.netty.design_pattern.chain_of_responsibility;

/**
 * @author liujiahan
 * @Title: ConsoleLogger
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/3
 * @ModifiedBy:
 */
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger (int level){
        this.level =level;
    }

    @Override
    protected void write(String message) {
        System.out.println("standard Console:logger:" + message);
    }
}
