package com.xfhy.recyclerviewrefresh.model;

/**
 * Created by xfhy on 2017/7/9.
 */

public class NewsBean {

    private String title;

    public NewsBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "title='" + title + '\'' +
                '}';
    }
}
