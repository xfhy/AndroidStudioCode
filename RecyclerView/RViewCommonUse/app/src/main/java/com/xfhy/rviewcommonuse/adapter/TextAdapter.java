package com.xfhy.rviewcommonuse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfhy.rviewcommonuse.R;

import java.util.List;

/**
 * Created by xfhy on 2017/9/9 9:41.
 * Description
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    private List<String> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTest;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTest = itemView.findViewById(R.id.tv_content);
        }
    }

    public TextAdapter(List<String> mData) {
        this.mData = mData;
    }

    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextAdapter.ViewHolder holder, int position) {
        holder.tvTest.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<String> getDataList() {
        return mData;
    }
}
