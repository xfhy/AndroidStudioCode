package com.xfhy.rxjavanetworktest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/7/30.
 */

public class Book {

    @SerializedName("id")
    private int id;
    @SerializedName("version")
    private double version;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
