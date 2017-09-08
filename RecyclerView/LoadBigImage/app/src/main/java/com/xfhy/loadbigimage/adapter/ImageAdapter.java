package com.xfhy.loadbigimage.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfhy.loadbigimage.DetailsActivity;
import com.xfhy.loadbigimage.DevicesUtil;
import com.xfhy.loadbigimage.R;
import com.xfhy.loadbigimage.util.GlideUtils;

import java.util.List;

/**
 * author feiyang
 * create at 2017/8/29 11:54
 * description：
 */
public class ImageAdapter extends BaseQuickAdapter<String, ImageAdapter.ImageViewHolder> {

    private RecyclerView recyclerView;

    class ImageViewHolder extends BaseViewHolder {
        private CardView cardView;

        public ImageViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cv_image_root_view);

            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.width = DevicesUtil.getDeviceWidth(mContext).widthPixels / 2 - 32;
            layoutParams.height = (int) (300 + Math.random() * 400);
            cardView.setLayoutParams(layoutParams);
        }
    }

    public ImageAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(R.layout.item_image, data);
    }

    public ImageAdapter(@Nullable List<String> data) {
        super(data);
    }

    public ImageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    protected void convert(ImageViewHolder helper, final String item) {
        helper.getView(R.id.cv_image_root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DetailsActivity.class);
                intent.putExtra("url", item);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(ImageViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        int childAdapterPosition = recyclerView.getChildAdapterPosition(holder.getView(R.id
                .cv_image_root_view));

        Log.e(TAG, childAdapterPosition + "-----------------------------------------------");

        //加入判断  上面的childAdapterPosition可能为-1
        if (childAdapterPosition < 0 || mData == null || childAdapterPosition > mData.size()) {
            return;
        }

        //用Glide加载图片
        GlideUtils.loadConsumImage(mContext, mData.get(childAdapterPosition), (ImageView) holder
                .getView(R.id.iv_image));

    }


}
