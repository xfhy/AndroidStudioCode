package com.xfhy.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xfhy.fragmentbestpractice.fragment.NewsContentFragment;

/**
 * 2017年2月23日22:00:11
 * 用于小屏幕  显示 新闻内容  的Activity   里面有一个碎片 用于显示新闻内容
 */
public class NewsContentActivity extends AppCompatActivity {

    /**
     * 启动当前Activity的最佳写法
     * @param context  启动这个Activity的上下文对象
     * @param newsTitle   新闻标题
     * @param newsContent  新闻内容
     */
    public static void actionStart(Context context, String newsTitle,String newsContent){
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        //Activity与fragment通信

        //1. 拿到传递过来的新闻标题和新闻内容
        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");

        //2. 找到当前布局中的fragment
        NewsContentFragment newsContentFragment = (NewsContentFragment)getSupportFragmentManager().
                findFragmentById(R.id.news_content_fragment);

        //3. 将数据传递到fragment中   fragment将数据显示到控件上
        newsContentFragment.refresh(newsTitle,newsContent);

    }
}
