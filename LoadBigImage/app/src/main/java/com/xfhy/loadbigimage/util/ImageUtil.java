package com.xfhy.loadbigimage.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * author feiyang
 * create at 2017/8/29 10:07
 * description：
 */
public class ImageUtil {

    /**
     * 计算压缩比率
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int
            reqHeight) {
        //原图片的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outWidth > reqWidth || outHeight > reqHeight) {
            //计算出实际宽高和目标宽高的比率
            int widthRatio = outWidth / reqWidth;
            int heightRatio = outHeight / reqHeight;
            inSampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    /**
     * 加载图片
     * @param resources
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int
            reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

}
