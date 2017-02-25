package com.xfhy.litepaltest.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by xfhy on 2017/2/25.
 */

public class Category extends DataSupport{

    private int id;
    private String category;
    private int categoryCode;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
