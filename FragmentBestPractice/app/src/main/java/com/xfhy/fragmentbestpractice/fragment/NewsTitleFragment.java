package com.xfhy.fragmentbestpractice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfhy.fragmentbestpractice.NewsContentActivity;
import com.xfhy.fragmentbestpractice.R;
import com.xfhy.fragmentbestpractice.bean.News;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xfhy on 2017/2/24.
 * 展示新闻列表的碎片
 */

public class NewsTitleFragment extends Fragment {

    /**
     * 是否是双页模式(即平板用的)
     */
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, null);

        //1. 找到RecyclerView控件
        RecyclerView newsTitleRecyclerView = (RecyclerView)view.
                findViewById(R.id.news_title_recycler_view);
        //2. 创建并设置LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(linearLayoutManager);

        //3. 创建并设置Adapter
        NewsAdapter newsAdapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(newsAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            //可以找到news_content_layout布局时,为双页模式
            isTwoPane = true;
        } else {
            //不可以找到news_content_layout布局时,为单页模式
            isTwoPane = false;
        }

    }

    //得到新闻数据
    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            News news = new News();
            news.setTitle("This is news title " + i);
            news.setContent(getRandomLengthContent("This is news content " + i + ". "));
            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomLengthContent(String content) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }
        return builder.toString();
    }

    /**
     * 作为RecyclerView的Adapter
     */
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        /**
         * 新闻标题列表
         */
        private List<News> mNewsList;

        public NewsAdapter(List<News> mNewsList) {
            this.mNewsList = mNewsList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View itemView) {
                super(itemView);
                newsTitleText = (TextView) itemView.findViewById(R.id.news_title);
            }
        }

        //创建缓存
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent,false);
            final ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取用户点击的子项的索引
                    News news = mNewsList.get(viewHolder.getAdapterPosition());
                    if (isTwoPane) {
                        //如果是双页模式,则刷新NewsContentFragment中的内容
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().
                                        findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        //如果是单页模式,则直接启动NewsContentActivity
                        NewsContentActivity.actionStart(getActivity(), news.getTitle(),
                                news.getContent());
                    }
                }
            });
            return viewHolder;
        }

        //绑定数据
        @Override
        public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();

        }
    }


}
