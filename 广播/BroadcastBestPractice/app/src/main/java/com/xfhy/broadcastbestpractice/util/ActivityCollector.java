package com.xfhy.broadcastbestpractice.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/2/24.
 * 管理所有活动的类   可以添加,移除,集体销毁    随时随地销毁所有的Activity
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 销毁所有Activity
     */
    public static void finishAll(){
        for (Activity activity : activities) {
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        //清空集合
        activities.clear();
    }

}
