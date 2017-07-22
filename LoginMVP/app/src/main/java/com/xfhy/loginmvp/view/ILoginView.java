package com.xfhy.loginmvp.view;

/**
 * Created by xfhy on 2017/7/1.
 */

public interface ILoginView {

    String getUserName();
    String getUserPass();

    void clearUserName();
    void clearUserPass();

    void showLoading();
    void hideLoading();

    /**
     * 登录成功时,跳转到相应的界面上去
     */
    void toMainActivity();

    /**
     * 显示错误信息   给用户友好提示
     */
    void showFaileError(Exception e);

}
