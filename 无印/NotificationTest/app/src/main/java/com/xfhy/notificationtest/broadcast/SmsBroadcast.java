package com.xfhy.notificationtest.broadcast;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.xfhy.notificationtest.NotificationActivity;
import com.xfhy.notificationtest.R;

import java.io.File;


/**
 * Created by xfhy on 2017/2/26.
 * 短信接收,发送的广播接收者
 */

public class SmsBroadcast extends BroadcastReceiver{

    private static final String TAG = "SmsBroadcast";
    private String messageBody;
    private String from;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            //接收到短信
            Log.i(TAG, "onReceive: RECEIVE_SMS");

            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            for (Object obj : objs) {
                byte[] pdu = (byte[]) obj;
                SmsMessage sms = SmsMessage.createFromPdu(pdu);
                //短信的内容
                messageBody = sms.getMessageBody();
                Log.i(TAG, "onReceive: "+ messageBody);
                //短信的发送方
                from = sms.getOriginatingAddress();
                Log.i(TAG, "onReceive: "+ from);

                notificate(context);  //通知
            }
        }
    }

    /**
     * 通知
     */
    private void notificate(Context context){
        //1. 获取NotificationManager对象
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //2.准备PendingIntent对象
        Intent intent =  new Intent(context, NotificationActivity.class);
        intent.putExtra("address",from);
        intent.putExtra("messageBody",messageBody);
        /**
         * 主要提供了几个静态方法获取PendingIntent的实例,可以根据具体的需求来选择是使用getActivity()方法,
         * getBroadcast()方法,还是getService()方法方法.这几个方法所接收的我参数都是相同的.第一个参数依旧是
         * Context,第二个参数一般用不到,通常传入0即可,第3个参数是Intent对象,我们可以通过这个对象构建出PendingIntent的意图
         * ,第4个参数用于确定PendingIntent的行为,具体的去查文档,一般传入0即可.
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        //3. 创建Notification对象
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(from)   //设置标题
                .setContentText(messageBody)   //设置内容
                .setWhen(System.currentTimeMillis())  //设置时间
                .setContentIntent(pendingIntent)     //设置点击事件  跳转
                .setAutoCancel(true)    //设置点击hour,自动取消通知
                .setSmallIcon(R.drawable.small_icon)  //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.large_icon))
                .build();
        //4.显示通知   参数:id,notification对象    要保证每个通知的id是不同的
        manager.notify(1,notification);

    }

}
