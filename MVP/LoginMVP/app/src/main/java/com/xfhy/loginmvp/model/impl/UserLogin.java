package com.xfhy.loginmvp.model.impl;

import com.xfhy.loginmvp.bean.User;
import com.xfhy.loginmvp.model.IUserLogin;
import com.xfhy.loginmvp.model.OnLoginListener;

/**
 * Created by xfhy on 2017/7/1.
 */

public class UserLogin implements IUserLogin {
    @Override
    public void login(final String userName, final String userPass, final OnLoginListener
            listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟访问服务器的耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (userName.equals("xfhy") && userPass.equals("123")) {
                    if (listener != null) {
                        User user = new User(userName, userPass);
                        listener.onSuccess(user);
                    }
                } else {
                    if (listener != null) {
                        listener.onError(new Exception("登录失败哦~"));
                    }
                }

            }
        }).start();
    }
}
