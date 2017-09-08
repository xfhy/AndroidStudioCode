package com.xfhy.recyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xfhy.recyclerviewtest.R;
import com.xfhy.recyclerviewtest.model.Fruit;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/2/21.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{

    private List<Fruit> mFruitList; //存放数据的集合
    private List<Integer> mHeights;

    //用于缓存
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;  //保存子项最外层布局的实例
        ImageView fruitImage;
        TextView fruitName;

        //构造方法
        public ViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;
            fruitImage = (ImageView)itemView.findViewById(R.id.iv_fruit_image);
            fruitName = (TextView)itemView.findViewById(R.id.tv_fruit_name);
        }
    }

    //构造方法  初始化数据
    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;

        //随机生成子项的高度
        mHeights = new ArrayList<>();
        for (int i = 0; i < mFruitList.size(); i++) {
            mHeights.add((int)(100+Math.random()*300));
        }
    }

    //创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        //设置子项的点击事件
        viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回此ViewHolder表示的项目的适配器位置
                int adapterPosition = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(adapterPosition);
                Toast.makeText(v.getContext(),"you clicked view "+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Fruit fruit = mFruitList.get(adapterPosition);
                Toast.makeText(v.getContext(), "you clicked image "+fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;   //创建ViewHolder对象并返回
    }

    //子项数据进行赋值,当滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置子项图片的高度
        ViewGroup.LayoutParams layoutParams = holder.fruitImage.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.fruitImage.setLayoutParams(layoutParams);

        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getImageId());
    }

    //多少个子项
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
