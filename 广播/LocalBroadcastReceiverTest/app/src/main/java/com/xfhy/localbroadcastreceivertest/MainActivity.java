package com.xfhy.localbroadcastreceivertest;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xfhy.localbroadcastreceivertest.receiver.LocalReceiver;

/**
 * 2017年2月24日16:11:34
 * 本地广播接收者
 * <p>Android引入了一套本地广播机制,使用这个机制发出的广播只能够在应用程序的内部进行传递,并且广播接收器也只能接收
 * 来自本程序发出的广播,这样所有的安全性问题就都不存在了.
 *
 * <p>
 * 主要是使用了一个LocalBroadcastManager来对广播进行管理
 */
public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. 获取LocalBroadcastManager实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Button bt_send_broadcast = (Button) findViewById(R.id.bt_send_broadcast);
        bt_send_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2. 发送本地广播
                Intent intent = new Intent();
                intent.setAction("com.xfhy.localbroadcast");
                intent.putExtra("name","xfhy");   //封装一条数据到Intent对象中
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        //3. 创建广播接收者    注册本地广播监听器
        localReceiver = new LocalReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.xfhy.localbroadcast");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //4. 取消注册广播
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
