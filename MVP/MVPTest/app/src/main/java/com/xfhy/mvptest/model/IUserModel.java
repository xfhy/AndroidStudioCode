package com.xfhy.mvptest.model;

import com.xfhy.mvptest.bean.UserBean;

/**
 * Created by xfhy on 2017/7/1.
 * 建立model（处理业务逻辑，这里指数据读写），先写接口，后写实现
 */

public interface IUserModel {

    void setID(int id);
    int getID();
    void setFirstName(String firstName);
    void setLastName(String lastName);

    /**
     * 通过id读取user信息,返回一个UserBean
     * @param id
     * @return
     */
    UserBean load(int id);

}
