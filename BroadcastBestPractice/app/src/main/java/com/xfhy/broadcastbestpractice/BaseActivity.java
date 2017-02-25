package com.xfhy.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xfhy.broadcastbestpractice.util.ActivityCollector;

/**
 * Created by xfhy on 2017/2/24.
 * 该类作为所有Activity的父类    方便管理
 * 有很多优点:比如知晓当前Activity的名称
 *
 *  为什么不在onCreate()和onDestroy()里注册和取消广播
 *  这是因为我们始终需要保证处于栈顶的活动才能接收到这条强制下线广播,非栈顶的活动不应该接收到这条广播
 *  ,所以写在onResume()和onPause()方法里就可以很好的解决这个问题,当一个活动失去栈顶位置时就会自动取消广播
 *  接收器的注册.
 *
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private ForceOffLineReceiver forceOffLineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //打印子类的类名
        Log.i(TAG, "onCreate: "+getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        //当Activity创建的时候加入
        ActivityCollector.addActivity(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //1. 创建广播接收者
        forceOffLineReceiver = new ForceOffLineReceiver();
        //2. 创建IntentFilter对象
        IntentFilter intentFilter = new IntentFilter();
        //3. 添加Action   注册监听发送该action的广播
        intentFilter.addAction("com.xfhy.broadcastbestpractice");
        //4. 注册广播
        registerReceiver(forceOffLineReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(forceOffLineReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁的时候移除
        ActivityCollector.removeActivity(this);
    }

    //定义广播接收者    收到广播,立刻
    class ForceOffLineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("警告!");
            builder.setMessage("强制下线,谢谢!");
            builder.setCancelable(false);    //设置不可按back键取消
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //销毁所有活动
                    ActivityCollector.finishAll();

                    //重新启动LoginActivity登录界面
                    Intent intent1 = new Intent(context,LoginActivity.class);
                    startActivity(intent1);
                }
            });

            //最后记得show()    显示该对话框
            builder.show();
        }
    }

}
