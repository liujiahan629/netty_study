package com.ljh.netty.design_pattern.chain_of_responsibility;

/**
 * @author liujiahan
 * @Title: ConsoleLogger
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/3
 * @ModifiedBy:
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level){
        this.level =level;
    }

    @Override
    protected void write(String message) {
        System.out.println("error Console:logger:" + message);
    }
}
