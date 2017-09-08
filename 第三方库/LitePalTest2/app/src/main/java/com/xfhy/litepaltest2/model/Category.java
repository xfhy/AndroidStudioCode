package com.xfhy.litepaltest2.model;

import org.litepal.crud.DataSupport;

/**
 * Created by xfhy on 2017/7/22.
 * 类别
 */

public class Category extends DataSupport {

    private int id;
    private String category;
    private int categoryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
