package com.xfhy.localbroadcastreceivertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by xfhy on 2017/2/24.
 */

public class LocalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        Toast.makeText(context, "我接收到一条广播,name数据为"+name, Toast.LENGTH_SHORT).show();
    }
}
