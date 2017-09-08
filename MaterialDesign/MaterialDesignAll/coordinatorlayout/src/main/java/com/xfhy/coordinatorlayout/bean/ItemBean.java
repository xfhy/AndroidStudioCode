package com.xfhy.coordinatorlayout.bean;

/**
 * Created by xfhy on 2017/6/11.
 */

public class ItemBean {

    private String content;

    public ItemBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "content='" + content + '\'' +
                '}';
    }
}
