package com.xfhy.fourcompnentdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xfhy.fourcompnentdemo.receiver.BookReceiver;
import com.xfhy.fourcompnentdemo.receiver.ContactReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_READ_CONTACTS = 1000;
    private Button mContactReceiverBtn;
    private Button mBookReceiverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestReadContactPermission();
    }

    /**
     * 申请联系人权限
     */
    private void requestReadContactPermission() {
        //先申请读取联系人权限
        //检查用户是否已经给我们授权了权限,相等则已经授权,不等则没授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat
                .checkSelfPermission(this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //参数:Context上下文,权限数组,申请码(申请码只要唯一就行)
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.READ_CONTACTS, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_CONTACTS);
        }
    }

    private void initView() {
        mContactReceiverBtn = (Button) findViewById(R.id.btn_test_receiver);
        mBookReceiverBtn = (Button) findViewById(R.id.btn_book);

        mContactReceiverBtn.setOnClickListener(this);
        mBookReceiverBtn.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    //申请权限成功
                    Log.d(TAG, "申请联系人权限成功");
                } else {
                    //申请权限失败
                    Toast.makeText(this, "亲~未授权读取联系人权限将无法使用本程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_receiver:
                Intent intent = new Intent();
                intent.setAction(ContactReceiver.TEST_ACTION);
                sendBroadcast(intent);
                break;
            case R.id.btn_book:
                Intent bookIntent = new Intent();
                bookIntent.setAction(BookReceiver.BOOK_RECEIVER_ACTION);
                sendBroadcast(bookIntent);
                break;
            default:
        }
    }
}
