package com.ljh.netty.design_pattern.proxy_pattern;

/**
 * @author liujiahan
 * @Title: RealImage
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName){
        this.fileName=fileName;
        loadFromDisk(fileName);
    }

    @Override
    public void display() {
        System.out.println("displaying" + fileName);
    }

    private void loadFromDisk(String fileName){
        System.out.println("loading" + fileName);
    }
}
