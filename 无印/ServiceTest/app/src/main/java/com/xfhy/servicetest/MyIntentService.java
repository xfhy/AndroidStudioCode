package com.xfhy.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * 2017年3月1日15:35:38
 * 运行结束后会自动停止
 */
public class MyIntentService extends IntentService {

    private final static String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");  //调用父类的有参构造函数
    }

    //这个方法是在子线程中运行    一般在这里处理一些具体的逻辑,不用担心ANR问题
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: 当前线程"+Thread.currentThread().getId());
        if (intent != null) {
        }
    }

    //根据IntentService的特性,在服务运行结束后会自动停止的
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: "+Thread.currentThread().getId());
        super.onDestroy();
    }
}
