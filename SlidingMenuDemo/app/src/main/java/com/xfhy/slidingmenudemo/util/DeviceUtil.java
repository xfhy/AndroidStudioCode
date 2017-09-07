package com.xfhy.slidingmenudemo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * author feiyang
 * create at 2017/8/30 9:00
 * description：
 */
public class DeviceUtil {

    /**
     * 获取屏幕大小
     * @param context
     * @return
     */
    public static DisplayMetrics getDeviceSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

}
