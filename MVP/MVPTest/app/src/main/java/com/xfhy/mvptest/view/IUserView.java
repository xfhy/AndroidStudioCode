package com.xfhy.mvptest.view;

/**
 * Created by xfhy on 2017/7/1.
 * 根据需求可知，View可以对ID、FirstName、LastName这三个EditText进行读操作，
 * 对FirstName和LastName进行写操作
 */

public interface IUserView {

    int getID();

    String getFirstName();

    String getLastName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

}
