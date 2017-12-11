package com.xfhy.hoteltest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feiyang on 2017/11/29 16:13
 * Description :
 */
public class KeyAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private final static int KEY_COUNT = 2;

    private final static int TYPE_HEADER = 1000;
    private final static int TYPE_NORMAL = 1001;


    private final LayoutInflater mLayoutInflater;
    private boolean mHasHistory = false;

    private List<KeyResponse> mData = new ArrayList<>();
    private boolean[] mIsExpand = new boolean[KEY_COUNT];
    /**
     * 组头的位置
     */
    private Integer[] mHeaderIndex = new Integer[KEY_COUNT];

    public KeyAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(@NonNull List<KeyResponse> datas) {
        this.mData = datas;
        if (datas == null) {
            return;
        }
        int outDataSize = datas.size();
        int headerCount = 0;
        for (int i = 0; i < outDataSize; i++) {
            KeyResponse keyResponse = datas.get(i);
            /*if (keyResponse.isHeader()) {
                mHeaderIndex[headerCount] = i;
                headerCount++;
            }*/
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = mLayoutInflater.inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_NORMAL) {
            view = mLayoutInflater.inflate(R.layout.item_normal, parent, false);
            return new NormalViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int result = 0;
        if (mData == null || mData.size() == 0) {
            return result;
        }
        int mDataSize = mData.size();
        for (int i = 0; i < mDataSize; i++) {
            KeyResponse keyResponse = mData.get(i);
            result += keyResponse.getNormalList().size() + 1;//+1是为了加header
        }

        return result;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mHeaderTv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mHeaderTv = itemView.findViewById(R.id.tv_header);
        }
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView mNormalTv;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mNormalTv = itemView.findViewById(R.id.tv_normal);
        }
    }

}
