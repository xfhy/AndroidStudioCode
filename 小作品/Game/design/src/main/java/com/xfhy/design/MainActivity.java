package com.xfhy.design;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
* Design
* @author xfhy
* create at 2017年6月23日16:47:39
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpScrolling(View view) {
        Intent intent = new Intent(this, ScrollingActivity.class);
        startActivity(intent);
    }
}
