package com.xfhy.lambdatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = (Button) findViewById(R.id.bt);


        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Log.i(TAG, "onCreate: Lumbda   表达式");
            }

        }).start();

    }
}
