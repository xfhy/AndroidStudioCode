package com.xfhy.drawsimplegaphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 2016年10月24日23:21:27
 * 简单绘制一下图形
 */
public class MainActivity extends AppCompatActivity {

    private Button button = null;
    public static boolean flag = false;
    private MyView myView = null;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.myBtn);
        myView = (MyView)findViewById(R.id.myView);
        myView.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flag = true;
                myView.setVisibility(View.VISIBLE);
            }
        });
    }
}
