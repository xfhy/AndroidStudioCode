package com.xfhy.notificationtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * 2017年2月26日10:50:58
 * 实现当短信到来时,显示通知
 * 将接收短信的广播,并读取短信数据,显示到通知上
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},1);
        } else {
            Log.i(TAG, "onCreate: 申请权限成功了的");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG, "onRequestPermissionsResult: 申请权限成功");
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: 申请权限失败");
                }
                break;
            default:
                break;
        }
    }
}
