package com.xfhy.recyclerviewrefresh.listener;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xfhy.recyclerviewrefresh.adapter.NewsAdapter;

import java.util.Random;


/**
 * Created by xfhy on 2017/6/16.
 * RecyclerView的滚动监听器
 */

public class EndLessOnScrollListener extends RecyclerView.OnScrollListener {
    private NewsAdapter mAdapter;
    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    //已经加载出来的Item的数量
    private int totalItemCount;

    //当前页，从0开始
    private int currentPage = 0;

    //主要用来存储上一个totalItemCount
    private int previousTotal = 0;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    private int firstVisibleItem;

    //是否正在上拉数据
    private boolean loading = true;

    /**
     * 需要请求的url
     */
    private String requestUrl;
    /**
     * 网络访问回调接口
     */
    private HttpCallbackListener listener;
    public static final int LOAD_MORE = 9999;

    public EndLessOnScrollListener(LinearLayoutManager mLinearLayoutManager, String requestUrl,
                                   HttpCallbackListener listener) {
        this.mLinearLayoutManager = mLinearLayoutManager;
        this.requestUrl = requestUrl;
        this.listener = listener;
    }

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public EndLessOnScrollListener(LinearLayoutManager mLinearLayoutManager, NewsAdapter adapter,
                                   String requestUrl,
                                   HttpCallbackListener listener) {
        this.mLinearLayoutManager = mLinearLayoutManager;
        this.mAdapter = adapter;
        this.requestUrl = requestUrl;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (loading) {

            if (totalItemCount > previousTotal) {
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
                mAdapter.changeMoreStatus(NewsAdapter.NO_LOAD_MORE);
            }
        }
        //这里需要好好理解  总条目数-可见条目数 <= 第一个可见条目
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++;
            //正在加载更多
            mAdapter.changeMoreStatus(NewsAdapter.LOADING_MORE);
            onLoadMore();
            loading = true;
        }
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        currentPage++;
        //请求网络数据 并通过接口回调回去
        //HttpUtils.requestGet(requestUrl+currentPage,LOAD_MORE,this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //模拟网络数据
                StringBuffer sb = new StringBuffer();
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    sb.append("我是新闻 " + random.nextInt(1000) + ",");
                }

                if (listener != null) {
                    listener.onFinish(LOAD_MORE, sb.toString());
                }

            }
        }).start();
    }
}
