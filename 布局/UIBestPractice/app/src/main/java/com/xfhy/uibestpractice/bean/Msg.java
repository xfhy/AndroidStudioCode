package com.xfhy.uibestpractice.bean;

/**
 * Created by xfhy on 2017/2/22.
 * Message 实体类对象
 */

public class Msg {

    //定义消息类型    接收  发送
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
