package com.xfhy.fragmentbestpractice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfhy.fragmentbestpractice.R;

/**
 * Created by xfhy on 2017/2/23.
 * 用于显示新闻内容的碎片   可以复用的    小屏幕和平板都可以用这个碎片来显示新闻内容
 */

public class NewsContentFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载
        view = inflater.inflate(R.layout.news_content_frag, null);
        return view;
    }

    //传入新闻数据   将新闻数据 显示到碎片的布局上
    public void refresh(String newsTitle, String newsContent) {
        //1. 设置显示新闻的LinearLayout可见
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);

        //2. 找到控件
        TextView tv_news_title = (TextView)view.findViewById(R.id.tv_news_title);
        TextView tv_news_content = (TextView)view.findViewById(R.id.tv_news_content);

        //3. 设置新闻数据显示到控件上
        tv_news_title.setText(newsTitle);    //刷新新闻的标题
        tv_news_content.setText(newsContent); //刷新新闻的内容
    }

}
