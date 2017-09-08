package com.xfhy.requestnetwork.global;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by xfhy on 2017/7/22.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //初始化xutils3
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
