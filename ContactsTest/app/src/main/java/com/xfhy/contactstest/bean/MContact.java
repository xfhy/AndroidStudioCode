package com.xfhy.contactstest.bean;

/**
 * Created by xfhy on 2017/2/25.
 * 联系人实体类
 */

public class MContact {

    private String name;
    private String phone;

    public MContact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
