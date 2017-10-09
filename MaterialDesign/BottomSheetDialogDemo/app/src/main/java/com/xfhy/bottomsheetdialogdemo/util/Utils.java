package com.xfhy.bottomsheetdialogdemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.xfhy.bottomsheetdialogdemo.entity.AppInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author feiyang
 * create at 2017/10/9 18:24
 * description：
 */
public class Utils {
    /**
     * 获取手机内所有支持分享的应用列表
     */
    public static ArrayList<AppInfoEntity> getShareAppList(Context context, Intent intent) {
        ArrayList<AppInfoEntity> shareAppInfoEntitys = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(context, intent);
        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfoEntity AppInfoEntity = new AppInfoEntity();
                AppInfoEntity.setPkgName(resolveInfo.activityInfo.packageName);
                AppInfoEntity.setLaunchClassName(resolveInfo.activityInfo.name);
                AppInfoEntity.setAppName(resolveInfo.loadLabel(packageManager).toString());
                AppInfoEntity.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfoEntitys.add(AppInfoEntity);
            }
        }
        return shareAppInfoEntitys;
    }

    /**
     * 查询手机内所有支持分享的应用列表
     */
    public static List<ResolveInfo> getShareApps(Context context, Intent intent) {
        List<ResolveInfo> resolveInfoList;
        PackageManager pm = context.getPackageManager();
        resolveInfoList = pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        // 按名称排序
//        ResolveInfo.DisplayNameComparator comparator = new ResolveInfo.DisplayNameComparator(pm);
//        Collections.sort(resolveInfoList, comparator);
        return resolveInfoList;
    }
}
