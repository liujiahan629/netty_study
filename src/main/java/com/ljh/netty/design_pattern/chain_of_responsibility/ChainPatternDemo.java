package com.ljh.netty.design_pattern.chain_of_responsibility;

/**
 * @author liujiahan
 * @Title: ChainPatternDemo
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/3
 * @ModifiedBy:
 */
public class ChainPatternDemo {

    private static AbstractLogger getChainOfLoggers(){
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;

    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();
        loggerChain.logMessage(AbstractLogger.INFO,"this is an information");
        loggerChain.logMessage(AbstractLogger.DEBUG,"this is an debug level information");
        loggerChain.logMessage(AbstractLogger.ERROR,"this is an error level information");
    }
}
