package com.xfhy.slidingmenudemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfhy.slidingmenudemo.R;
import com.xfhy.slidingmenudemo.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * author feiyang
 * create at 2017/8/30 9:34
 * description：
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements SlidingMenu
        .MenuStateListener {

    private List<String> mData;
    private SlidingMenu mOpenMenu;

    public MyAdapter(List<String> mData) {
        this.mData = mData;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        TextView tvTop;
        TextView tvDelete;
        SlidingMenu slidingMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            slidingMenu = (SlidingMenu) itemView.findViewById(R.id.sliding_menu);
            tvContent = (TextView) itemView.findViewById(R.id.tv_item_content);
            tvTop = (TextView) itemView.findViewById(R.id.tv_menu);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_menu_delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String content = mData.get(position);
        holder.slidingMenu.setOnMenuStateListener(this);
        holder.tvContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void openSlideMenu(SlidingMenu slidingMenu) {
        if (mOpenMenu != null && mOpenMenu != slidingMenu) {
            mOpenMenu.closeMenu();
        }
        mOpenMenu = slidingMenu;
        Log.e("xfhy", "openSlideMenu");
    }

    @Override
    public void closeOtherSlideMenu(SlidingMenu slidingMenu) {
        if (mOpenMenu != null && mOpenMenu != slidingMenu) {
            mOpenMenu.closeMenu();
            Log.e("xfhy", "closeOtherSlideMenu");
        }
    }
}
