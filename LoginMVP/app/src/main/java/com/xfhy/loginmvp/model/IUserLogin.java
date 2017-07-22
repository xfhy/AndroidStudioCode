package com.xfhy.loginmvp.model;

/**
 * Created by xfhy on 2017/7/1.
 * <p>
 * <p>
 * model接口,需要有登录的方法,然后登录是否成功或失败
 */

public interface IUserLogin {

    /**
     * 登录
     * @param userName 用户名
     * @param userPass 密码
     * @param listener 登录状态接口
     */
    void login(String userName, String userPass, OnLoginListener listener);

}
