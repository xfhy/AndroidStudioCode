package com.xfhy.loginmvp.presenter;

import android.os.Handler;

import com.xfhy.loginmvp.bean.User;
import com.xfhy.loginmvp.model.OnLoginListener;
import com.xfhy.loginmvp.model.impl.UserLogin;
import com.xfhy.loginmvp.view.ILoginView;

/**
 * Created by xfhy on 2017/7/1.
 */

public class LoginPresenter implements OnLoginListener {

    /**
     * 这是View的接口(Activity实现这个接口)
     */
    private ILoginView mLoginView;
    /**
     * 这是Model
     */
    private UserLogin mUserLogin;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginView mLoginView) {
        this.mLoginView = mLoginView;
        mUserLogin = new UserLogin();
    }

    /**
     * 登录
     */
    public void login() {
        mLoginView.showLoading();
        mUserLogin.login(mLoginView.getUserName(), mLoginView.getUserPass(), this);
    }

    /**
     * 清空输入
     */
    public void clear() {
        mLoginView.clearUserName();
        mLoginView.clearUserPass();
    }

    @Override
    public void onSuccess(User user) {

        //post()方法:将Runnable中的内容添加到消息队列中,然后将运行在handler依附在的线程上
        //这里这样写的原因是可能是在子线程中运行的,所以为了确保在UI线程运行,就这样写

        //以下需要在UI线程执行
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //登录成功
                //跳转到登录成功的界面
                mLoginView.toMainActivity();
                //隐藏进度条
                mLoginView.hideLoading();
            }
        });
    }

    @Override
    public void onError(final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //登录失败
                //显示错误信息
                mLoginView.showFaileError(e);
                //隐藏进度条
                mLoginView.hideLoading();
            }
        });
    }
}
