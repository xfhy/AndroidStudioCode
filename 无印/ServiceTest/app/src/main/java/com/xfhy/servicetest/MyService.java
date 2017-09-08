package com.xfhy.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        /**
         * 模拟的下载资源的方法
         */
        public void startDownload(){
            Log.i(TAG, "startDownload: executed");
        }

        /**
         * 模拟的下载资源时的获取下载进度的方法
         * @return
         */
        public int getProgress(){
            Log.i(TAG, "getProgress: executed");
            return 0;
        }

    }

    public MyService() {
        Log.i(TAG, "MyService: ");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //设置当前Service为前台服务
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")  //设置标题
                .setContentText("this is content text")   //设置内容
                .setWhen(System.currentTimeMillis())      //设置时间
                .setSmallIcon(R.mipmap.ic_launcher)     //设置状态栏小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)) //大图标
                .setContentIntent(pendingIntent)  //设置点击时跳转事件
                .build();  //创建
        startForeground(1,notification);   //开始前台服务    让MyService变成一个前台服务,并在状态栏显示出来

        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
