package com.xfhy.recyclerviewtest.model;

/**
 * Created by xfhy on 2017/2/21.
 *
 * 这是水果的bean类
 */

public class Fruit {

    private String name;   //水果名
    private int imageId;   //水果图片

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
