package com.xfhy.fourcompnentdemo;

import android.app.Application;
import android.content.Context;

/**
 * @author feiyang
 *         time create at 2017/11/28 14:10
 *         description
 */
public class AppApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }

}
