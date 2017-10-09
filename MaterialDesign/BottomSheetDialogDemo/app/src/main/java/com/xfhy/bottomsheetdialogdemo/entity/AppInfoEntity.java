package com.xfhy.bottomsheetdialogdemo.entity;

import android.graphics.drawable.Drawable;

/**
 * author feiyang
 * create at 2017/10/9 18:03
 * description：app信息类
 */
public class AppInfoEntity {

    /**
     * 包名
     */
    private String pkgName;
    /**
     * 打开的class
     */
    private String launchClassName;
    /**
     * app名称
     */
    private String appName;
    /**
     * app图标
     */
    private Drawable appIcon;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getLaunchClassName() {
        return launchClassName;
    }

    public void setLaunchClassName(String launchClassName) {
        this.launchClassName = launchClassName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
