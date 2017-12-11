package com.xfhy.hoteltest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xfhy.hoteltest.KeyResponse;
import com.xfhy.hoteltest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xfhy
 *         create at 2017/11/29 22:01
 *         description：
 */
public class KeySectionAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder,
        KeySectionAdapter.HeaderViewHolder, KeySectionAdapter.NormalViewHolder, RecyclerView
        .ViewHolder,
        RecyclerView
                .ViewHolder> {

    private List<KeyResponse> mData = new ArrayList<>();
    private Context mContext;

    private final static int KEY_COUNT = 2;
    private final static int TYPE_HEADER = 1000;
    private final static int TYPE_NORMAL = 1001;
    /**
     * 记录该组是否已经展开
     */
    private boolean[] mIsExpand = new boolean[KEY_COUNT];
    private final LayoutInflater mLayoutInflater;
    private boolean mHasHistory = false;

    public KeySectionAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void setmData(List<KeyResponse> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    protected boolean hasHeader() {
        return false;
    }

    @Override
    protected int getSectionCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (mData == null || mData.size() < section) {
            return 0;
        }
        KeyResponse keyResponse = mData.get(section);
        List<String> normalList = keyResponse.getNormalList();
        int size = normalList.size();
        int result = 0;
        if (mIsExpand[section]) {
            result = size;
        } else {
            result = 8;
        }

        return result;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int
            viewType) {
        return null;
    }

    @Override
    protected NormalViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_normal, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, final int section) {
        holder.mHeaderTv.setText(mData.get(section).getHeaderText());
        holder.mHeaderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsExpand[section] = !mIsExpand[section];
                Toast.makeText(mContext, "点头了  section=" + section, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onBindItemViewHolder(NormalViewHolder holder, final int section, final int
            position) {
        List<String> normalList = mData.get(section).getNormalList();
       // if (mIsExpand[section]) {
           // holder.mNormalTv.setText(normalList.get(position));
        //} else {
            //没有展开
            holder.mNormalTv.setText(normalList.get(position));
       // }
        holder.mNormalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "section=" + section + "  position=" + position, Toast
                        .LENGTH_SHORT)
                        .show();
            }
        });

    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindFooterOtherViewHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        int result = 0;
        int mDataSize = mData.size();
        for (int i = 0; i < mDataSize; i++) {
            KeyResponse keyResponse = mData.get(i);
            if (mIsExpand[i]) {  //是展开 则显示全部
                result += keyResponse.getNormalList().size() + 1;
            } else {
                result += 9;
            }
        }
        Log.e("TAG", "result=" + result);
        return result;
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
