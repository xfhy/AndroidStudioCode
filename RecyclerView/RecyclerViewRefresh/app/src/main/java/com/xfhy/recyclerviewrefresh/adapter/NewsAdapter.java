package com.xfhy.recyclerviewrefresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xfhy.recyclerviewrefresh.R;
import com.xfhy.recyclerviewrefresh.model.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/7/9.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View
        .OnClickListener {

    private static final String TAG = "NewsAdapter";
    private List<NewsBean> newsBeanList;

    private Context context;
    private LayoutInflater layoutInflater;

    /**
     * 正常布局
     */
    private static final int TYPE_NORMAL_ITEM = 10000;
    /**
     * 底部布局
     */
    private static final int TYPE_FOOT_ITEM = 10001;

    /**
     * 上拉加载更多
     */
    public static final int PULL_UP_LOAD_MORE = 1000;
    /**
     * 正在加载更多
     */
    public static final int LOADING_MORE = 1001;
    /**
     * 没有在加载更多
     */
    public static final int NO_LOAD_MORE = 1002;

    /**
     * 上拉加载的默认状态
     */
    private int mLoadMoreStatus = PULL_UP_LOAD_MORE;

    /**
     * 子项点击事件的接口
     */
    private OnItemClickListener mOnItemClickListener = null;

    /**
     * 子项的点击事件监听器
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public NewsAdapter(Context context, List<NewsBean> newsBeanList) {
        this.context = context;
        this.newsBeanList = newsBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 正常子项的ViewHolder
     */
    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
        }
    }

    /**
     * 尾布局的ViewHolder
     */
    static class FootViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootView;
        ProgressBar progressBar;
        TextView loadInfo;

        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            loadInfo = (TextView) itemView.findViewById(R.id.tv_load_info);
            rootView = (LinearLayout) itemView.findViewById(R.id.ll_foot);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //点击事件   回调
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    /**
     * 设置子项点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断类型   加载不同的布局
        if (viewType == TYPE_FOOT_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_foot_loadmore, parent, false);
            view.setOnClickListener(this);
            return new FootViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_normal_news, parent, false);
            view.setOnClickListener(this);
            return new NewsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (newsBeanList != null && newsBeanList.size() > position) {
            if (holder instanceof NewsViewHolder) {
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                NewsBean newsBean = newsBeanList.get(position);
                newsViewHolder.newsTitle.setText(newsBean.getTitle());
                //设置tag,待会儿可以在点击事件时获取其位置
                newsViewHolder.itemView.setTag(position);
            } else if (holder instanceof FootViewHolder) {
                FootViewHolder footHolder = (FootViewHolder) holder;

                switch (mLoadMoreStatus) {
                    case PULL_UP_LOAD_MORE:
                        footHolder.loadInfo.setText("上拉加载更多");
                        break;
                    case LOADING_MORE:
                        footHolder.loadInfo.setText("正在加载...");
                        break;
                    case NO_LOAD_MORE:
                        footHolder.rootView.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (newsBeanList != null && newsBeanList.size() != 0) ? newsBeanList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (newsBeanList == null) {
            return 0;
        }
        if (position < newsBeanList.size()) {
            return TYPE_NORMAL_ITEM;
        } else {
            return TYPE_FOOT_ITEM;
        }
    }

    /**
     * 更新数据
     *
     * @param newData
     */
    public void updateNewsData(List<NewsBean> newData) {
        if (newData == null) {
            return;
        }
        if (newsBeanList == null) {
            newsBeanList = new ArrayList<>();
        }
        newsBeanList.clear();
        newsBeanList.addAll(newData);
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param newData
     */
    public void addNewsData(List<NewsBean> newData) {
        if (newData == null) {
            return;
        }
        if (newsBeanList == null) {
            newsBeanList = new ArrayList<>();
        }
        newsBeanList.addAll(newData);
        this.notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }

}
