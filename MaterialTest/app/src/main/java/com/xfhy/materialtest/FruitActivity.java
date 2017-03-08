package com.xfhy.materialtest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView fruit_image_view = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruit_content_text = (TextView) findViewById(R.id.fruit_content_text);

        setSupportActionBar(toolbar);   //设置标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);    //设置导航图标可见
        }
        collapsingToolbar.setTitle(fruitName);   //设置标题栏  标题
        Glide.with(this).load(fruitImageId).into(fruit_image_view);   //设置显示水果图片

        String fruitContent = generateFruitContent(fruitName);
        fruit_content_text.setText(fruitContent);   //设置水果介绍需要显示的文字
    }

    /**
     * 生成水果的介绍
     * @param fruitName
     * @return
     */
    private String generateFruitContent(String fruitName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(fruitName);
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:   //这是导航按钮的id(固定值)
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
