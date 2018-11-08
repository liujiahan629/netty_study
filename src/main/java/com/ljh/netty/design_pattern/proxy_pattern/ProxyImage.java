package com.ljh.netty.design_pattern.proxy_pattern;

/**
 * @author liujiahan
 * @Title: ProxyImage
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/11/8
 * @ModifiedBy:
 */
public class ProxyImage implements Image {

    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName){
        this.fileName = fileName;
    }


    @Override
    public void display() {
        if(realImage==null){
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
