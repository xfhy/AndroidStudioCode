package com.xfhy.gradledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //代码里面可以直接获取defaultConfig下buildConfigField值
        Log.e(TAG,"配置的地址为:"+BuildConfig.API_SERVER_URL);
    }
}
