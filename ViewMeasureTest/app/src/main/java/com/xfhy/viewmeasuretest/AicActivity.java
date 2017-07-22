package com.xfhy.viewmeasuretest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AicActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_air_display);

    ArcDisplay arc = (ArcDisplay) findViewById(R.id.arc);
    arc.setSweepValue(250);   //设置顺时针扫过过角度
  }
}
