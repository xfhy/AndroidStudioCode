package com.xfhy.mvptest.presenter;

import com.xfhy.mvptest.bean.UserBean;
import com.xfhy.mvptest.model.IUserModel;
import com.xfhy.mvptest.model.impl.UserModel;
import com.xfhy.mvptest.view.IUserView;

/**
 * Created by xfhy on 2017/7/1.
 * 建立presenter（主导器，通过iView和iModel接口操作model和view），
 * activity可以把所有逻辑给presenter处理，这样java
 * 逻辑就从手机的activity中分离出来。
 */

public class UserPresenter {

    /**
     * 操作View
     */
    private IUserView mUserView;
    /**
     * 操作数据
     */
    private IUserModel mUserModel;

    public UserPresenter(IUserView userView) {
        this.mUserView = userView;
        mUserModel = new UserModel();
    }

    /**
     * 通过model保存User数据
     * @param id
     * @param firstName
     * @param lastName
     */
    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);
    }

    /**
     * 通过model读取User数据并设置到界面上
     * @param id
     */
    public void loadUser(int id) {
        UserBean userBean = mUserModel.load(id);
        mUserView.setFirstName(userBean.getFirstName());
        mUserView.setLastName(userBean.getLastName());
    }

}
