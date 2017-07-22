package com.xfhy.requestnetwork.bean;

/**
 * Created by xfhy on 2017/7/22.
 */

public class Book {

    private int id;
    private String version;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
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
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
