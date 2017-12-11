package com.xfhy.fourcompnentdemo.util;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.xfhy.fourcompnentdemo.AppApplication;

/**
 * @author xfhy
 *         create at 2017/11/28 21:12
 *         description：
 */
public class ToastUtil {

    /**
     * 展示Toast
     *
     * @param message 需要显示的message
     */
    public static void showMessage(@NonNull String message) {
        Toast.makeText(AppApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

}
