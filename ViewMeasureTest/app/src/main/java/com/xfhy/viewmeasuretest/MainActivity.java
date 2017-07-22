package com.xfhy.viewmeasuretest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TopBar.topbarClickListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView myview = (MyView) findViewById(R.id.myview);
        TopBar tb_bar = (TopBar) findViewById(R.id.tb_bar);


        tb_bar.setOnTopbarClickListener(this);

        tb_bar.setButtonVisable(0,true);
        tb_bar.setButtonVisable(1,false);

    }

    @Override public void leftClick() {
        Toast.makeText(this, "点击了左方按钮", Toast.LENGTH_SHORT).show();
    }

    @Override public void rightClick() {
        Toast.makeText(this, "点击了右方按钮", Toast.LENGTH_SHORT).show();
    }
}
