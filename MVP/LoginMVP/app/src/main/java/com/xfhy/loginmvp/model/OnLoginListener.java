package com.xfhy.loginmvp.model;

import com.xfhy.loginmvp.bean.User;

/**
 * Created by xfhy on 2017/7/1.
 * 登录的结果
 */

public interface OnLoginListener {

    /**
     * 登录成功
     * @param user   登录成功的用户
     */
    void onSuccess(User user);

    /**
     * 登录失败
     * @param e  失败原因
     */
    void onError(Exception e);

}
