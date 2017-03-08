package com.xfhy.materialtest.model;

/**
 * Created by xfhy on 2017/3/6.
 * 水果实体类
 */

public class Fruit {

    private String name;
    private int imageId;

    //构造方法    快速生成构造方法:alt+insert
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
