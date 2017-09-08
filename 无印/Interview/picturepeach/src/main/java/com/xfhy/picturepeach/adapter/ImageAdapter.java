package com.xfhy.picturepeach.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xfhy.picturepeach.R;
import com.xfhy.picturepeach.entity.Image;
import com.xfhy.picturepeach.util.AppUtils;

import java.util.List;

/**
 * Created by xfhy on 2017/6/29.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> implements View
        .OnClickListener {

    private static final String TAG = "ImageAdapter";
    private List<Image> dataList;
    private Context context;
    private int screenWidth;
    private int screenHeight;

    @Override
    public void onClick(View v) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    public ImageAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.dataList = imageList;

        DisplayMetrics displayMetrics = AppUtils.getAppWidth(context);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_imageview, parent, false);

        //这里需要动态设置子项的宽度和高度  以使其保持一致,然后是正方形
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view
                .getLayoutParams();

        layoutParams.width = screenWidth / 2;
        layoutParams.height = layoutParams.width;
        view.setLayoutParams(layoutParams);

        //设置改子项的点击事件
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            String imagePath = dataList.get(position)
                    .getImagePath();

            //1.获取图片的分辨率  宽和高
            //创建bitmap工厂的配置参数
            BitmapFactory.Options option = new BitmapFactory.Options();
            //这个设置为true 则不返回bitmap,直接返回null  然后decodeFile()方法将图片的信息封装到Options里面
            option.inJustDecodeBounds = true;
            //根据路径加载图片  将图片转换成bitmap
            BitmapFactory.decodeFile(imagePath,option);
            int imgWidth = option.outWidth;
            int imgHeight = option.outHeight;

            //3.计算缩放比   到底缩放多少合适
            int scale = 1;
            int scalex = imgWidth/screenWidth;
            int scaley = imgHeight/screenHeight;

//            Log.e(TAG, "onBindViewHolder: imgWidth:"+imgWidth+"  imgHeight:"+imgHeight);
            Log.e(TAG, "onBindViewHolder: scalex:"+scalex+"  scaley:"+scaley);

            //挑选其中缩放比较大的来
            if(scalex>scale && scalex>scaley){
                scale = scalex;
            }
            if(scaley>scale && scaley>scalex){
                scale = scaley;
            }

            //4.设置图片的缩放比 ,用来节约内存
            option.inSampleSize = scale;
            option.inJustDecodeBounds = false;   //设置这个为false   不然会返回null
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);


            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
