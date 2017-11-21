package com.xfhy.canvasoperation;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private TaiChiView taiChiView;
    private static int degress = 0;
    private static MyHander mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeakReference<Activity> weakReference = new WeakReference<Activity>(this);
        mHander = new MyHander(weakReference);
        taiChiView = findViewById(R.id.tch_test);
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                degress += 5;
                mHander.sendEmptyMessage(1);
            }
        }, 100);
    }

    public void ratate() {
        taiChiView.setRotate(degress);
        Log.e("xx","xx");
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                degress += 5;
                mHander.sendEmptyMessage(1);
            }
        }, 100);
    }

    static class MyHander extends Handler {

        WeakReference<Activity> weakReference;

        public MyHander(WeakReference<Activity> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = weakReference.get();
            if (activity != null) {
                ((MainActivity) activity).ratate();
            }
        }
    }

}
