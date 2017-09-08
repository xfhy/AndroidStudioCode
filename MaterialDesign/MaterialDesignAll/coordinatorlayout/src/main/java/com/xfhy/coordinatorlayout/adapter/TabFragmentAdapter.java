package com.xfhy.coordinatorlayout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfhy.coordinatorlayout.R;
import com.xfhy.coordinatorlayout.bean.ItemBean;

import java.util.List;

/**
 * Created by xfhy on 2017/6/11.
 * RecyclerView的Adapter
 */

public class TabFragmentAdapter extends RecyclerView.Adapter<TabFragmentAdapter.ViewHolder> {

    private List<ItemBean> dataList;

    public TabFragmentAdapter(List<ItemBean> dataList) {
        this.dataList = dataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.tv_item_content);
        }
    }

    @Override
    public TabFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_bean_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabFragmentAdapter.ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            ItemBean itemBean = dataList.get(position);
            String content = itemBean.getContent();
            holder.itemText.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }
}
