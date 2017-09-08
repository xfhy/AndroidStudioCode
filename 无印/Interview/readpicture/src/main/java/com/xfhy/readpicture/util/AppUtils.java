package com.xfhy.readpicture.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by xfhy on 2017/6/18.
 */

public class AppUtils {

    /**
     * 获取手机屏幕的高度和宽度
     *
     * @return DisplayMetrics对象, displayMetrics.widthPixels是宽度, 显示的绝对宽度（以像素为单位）
     */
    public static DisplayMetrics getAppWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

}
