package com.xfhy.broadcastbestpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private Button bt_sendbroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_sendbroadcast = (Button)  findViewById(R.id.bt_sendbroadcast);

        bt_sendbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送广播进行强制下线
                Intent intent = new Intent("com.xfhy.broadcastbestpractice");  //里面的是action
                sendBroadcast(intent);
            }
        });

    }
}
