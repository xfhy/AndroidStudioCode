package com.xfhy.loadbigimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImage;
    private int startX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        initIntentData();
    }

    private void initIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            if (!TextUtils.isEmpty(url)) {
                Glide.with(this).load(url).into(mImage);
            }
        }
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.iv_details);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                if (moveX - startX > 100) {
                    finish();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
