package com.xfhy.recyclerviewrefresh;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xfhy.recyclerviewrefresh.adapter.NewsAdapter;
import com.xfhy.recyclerviewrefresh.listener.EndLessOnScrollListener;
import com.xfhy.recyclerviewrefresh.listener.HttpCallbackListener;
import com.xfhy.recyclerviewrefresh.model.NewsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, HttpCallbackListener {

    private static final int PULL_UP_LOAD_FINISHED = 1000;
    private static final int LOAD_MORE_FINISHED = 1001;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Toolbar mToolbar;
    private boolean isLoading = false;
    private SwipeRefreshLayout mSwipe;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PULL_UP_LOAD_FINISHED:
                    List<NewsBean> newsBeanList = (List<NewsBean>) msg.obj;
                    mNewsAdapter.updateNewsData(newsBeanList);
                    mSwipe.setRefreshing(false);
                    isLoading = false;
                    break;
                case LOAD_MORE_FINISHED:
                    List<NewsBean> moreNewsBeanList = (List<NewsBean>) msg.obj;
                    mNewsAdapter.addNewsData(moreNewsBeanList);
                    isLoading = false;
                    //设置尾布局为没有在加载更多
                    mNewsAdapter.changeMoreStatus(NewsAdapter.NO_LOAD_MORE);
                    break;
            }
        }
    };
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_news);

        setSupportActionBar(mToolbar);

        //下拉刷新的颜色
        mSwipe.setColorSchemeColors(getResources().getColor(R.color.blueStatus));
        mSwipe.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        //正常布局的adapter
        mNewsAdapter = new NewsAdapter(mContext, getData());

        mNewsAdapter.setOnItemClickListener(this);

        //设置添加了头布局尾布局的adapter
        mRecyclerView.setAdapter(mNewsAdapter);

        //给RecyclerView添加滑动监听
        EndLessOnScrollListener endLessOnScrollListener = new EndLessOnScrollListener
                (layoutManager,mNewsAdapter, "dajdhasjdahskj.com", this);
        mRecyclerView.addOnScrollListener(endLessOnScrollListener);

    }

    private List<NewsBean> getData() {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewsBean newsBean = new NewsBean("我是新闻 " + i);
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }

    //RecyclerView的子项点击事件  反馈  函数
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext, "Click " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //Toolbar左上角的按钮
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        //如果正在进行上一次的刷新,则不进入
        if (isLoading) {
            return;
        }
        isLoading = true;

        //模拟访问网络获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {

                //模拟访问网络耗时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<NewsBean> newsBeanList = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < 20; i++) {
                    NewsBean newsBean = new NewsBean("我是新闻 " + random.nextInt(1000));
                    newsBeanList.add(newsBean);
                }

                Message msg = Message.obtain();
                msg.obj = newsBeanList;
                msg.what = PULL_UP_LOAD_FINISHED;
                mHandler.sendMessage(msg);
            }
        }).start();

    }

    @Override
    public void onFinish(int from, String response) {
        if(from==EndLessOnScrollListener.LOAD_MORE){
            if (response != null) {
                String[] split = response.split(",");
                List<NewsBean> newsBeanList = new ArrayList<>();
                for (String news : split) {
                    NewsBean newsBean = new NewsBean(news);
                    newsBeanList.add(newsBean);
                }
                Message msg = Message.obtain();
                msg.obj = newsBeanList;
                msg.what = LOAD_MORE_FINISHED;
                mHandler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onError(Exception e) {

    }
}
