package com.xfhy.fourcompnentdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xfhy.fourcompnentdemo.service.ContactService;

/**
 * @author feiyang
 *         time create at 2017/11/28 11:25
 *         description
 */
public class ContactReceiver extends BroadcastReceiver {

    public final static String TEST_ACTION = "com.xfhy.test.receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TEST_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "收到广播", Toast.LENGTH_SHORT).show();
            Intent serviceIntent = new Intent();
            serviceIntent.setClass(context, ContactService.class);
            context.startService(serviceIntent);
        }
    }
}
