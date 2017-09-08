package com.xfhy.notificationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView tv_address = (TextView) findViewById(R.id.tv_address);
        TextView tv_message_body = (TextView) findViewById(R.id.tv_message_body);

        //获取传递过来的短信数据
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String messageBody = intent.getStringExtra("messageBody");

        tv_address.setText(address);
        tv_message_body.setText(messageBody);
    }
}
