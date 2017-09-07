package com.xfhy.loadbigimage.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.xfhy.loadbigimage.R;

/**
 * author feiyang
 * create at 2017/8/29 12:32
 * description：
 */
public class GlideUtils {

    private static RequestOptions requestOptions = new RequestOptions();

    /**
     * 加载图片   设置所有东西  方便加载
     *
     * @param path
     * @param mImageView
     */
    public static void loadConsumImage(Context context, String path, ImageView mImageView) {
        //加载成功图片
        // 加载失败图片
        // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
        //动画
        Glide.with(context)
                .load(path)
                .apply(requestOptions
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(mImageView);
    }

}
