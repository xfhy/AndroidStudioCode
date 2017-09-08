package com.xfhy.picturepeach.entity;

/**
 * Created by xfhy on 2017/6/29.
 */

public class Image {

    private String imageName;
    private String imagePath;
    //获取图片的详细信息
    private String imageDesc;

    public Image(String imageName, String imagePath, String imageDesc) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageDesc = imageDesc;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageName='" + imageName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageDesc='" + imageDesc + '\'' +
                '}';
    }
}
