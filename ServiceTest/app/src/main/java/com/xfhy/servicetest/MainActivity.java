package com.xfhy.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 2017年3月1日12:48:55
 * 服务   test
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button bt_start_service;
    private Button bt_stop_service;
    private Button bt_bind_service;
    private Button bt_unbind_service;
    private Button bt_start_intent_service;

    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_start_service = (Button) findViewById(R.id.bt_start_service);
        bt_stop_service = (Button) findViewById(R.id.bt_stop_service);
        bt_bind_service = (Button) findViewById(R.id.bt_bind_service);
        bt_unbind_service = (Button) findViewById(R.id.bt_unbind_service);
        bt_start_intent_service = (Button) findViewById(R.id.bt_start_intent_service);


        bt_start_service.setOnClickListener(this);
        bt_stop_service.setOnClickListener(this);
        bt_bind_service.setOnClickListener(this);
        bt_unbind_service.setOnClickListener(this);
        bt_start_intent_service.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
            case R.id.bt_start_service:
                startService(intent);
                break;
            case R.id.bt_stop_service:
                stopService(intent);
                break;
            case R.id.bt_bind_service:
                //绑定服务
                //参数:Intent,ServiceConnection实例,flag     这里是BIND_AUTO_CREATE表示绑定后自动创建服务
                bindService(intent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind_service:
                //解绑服务
                unbindService(connection);
                break;
            case R.id.bt_start_intent_service:
                Log.i(TAG, "onClick: 主线程id"+Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
        }
    }

    /**
     * 监听服务的连接状态
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: 连接上服务");
            //成功绑定服务时调用
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
            Log.i(TAG, "onServiceDisconnected: 断开连接");
        }
    };

}
