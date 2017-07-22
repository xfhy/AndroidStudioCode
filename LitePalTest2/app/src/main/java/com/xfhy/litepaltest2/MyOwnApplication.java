package com.xfhy.litepaltest2;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by xfhy on 2017/7/22.
 */

public class MyOwnApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化LitePal
        LitePal.initialize(this);
    }
}
