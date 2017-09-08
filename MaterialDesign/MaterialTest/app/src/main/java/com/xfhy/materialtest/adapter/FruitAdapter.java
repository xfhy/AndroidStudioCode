package com.xfhy.materialtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xfhy.materialtest.FruitActivity;
import com.xfhy.materialtest.R;
import com.xfhy.materialtest.model.Fruit;

import java.util.List;

/**
 * Created by xfhy on 2017/3/6.
 * RecyclerView的适配器
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {


    private Context mContext;

    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }

    //创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        //加载布局到view
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        //设置每个卡片的点击事件
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(adapterPosition);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });

        return viewHolder;   //创建ViewHolder实例
    }

    //绑定数据   子项数据进行赋值,当滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());


        /*
        * Glide的用法
        * 首先调用Glide.with()方法并传入Context,Activity,Fragment参数,然后调用load()方法去加载图片
        * 可以是一个URL地址,也可以是一个本地地址,也可以是一个资源id.最后调用into()方法将图片
        * 设置到具体某一个ImageView中就可以了.
        *
        * 为什么要使用Glide:这里的图片像素比较高,如果不进行压缩就直接显示的话,很容易就会引起内存溢出.而使用Glide
        * 就完全不需要担心这回事,因为Glide在内部做了许多负责的逻辑操作,其中就包括了图片压缩,我们
        * 只需要安心按照Glide的标准用法去加载图片就可以了.
        * */
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);   //加载图片
    }

    //多少个子项
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
