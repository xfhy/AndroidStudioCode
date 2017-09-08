package com.xfhy.loadbigimage.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xfhy.loadbigimage.DevicesUtil;
import com.xfhy.loadbigimage.R;

/**
 * author feiyang
 * create at 2017/8/29 15:53
 * description：
 */
public class LoadingProgressDialog extends AlertDialog {
    /**
     * 动画的资源文件
     */
    private int mResId;
    /**
     * 对话框的标题
     */
    private String mLoadingTitle;
    private Context mContext;
    /**
     * 显示图片
     */
    private ImageView mImage;
    /**
     * 显示标题
     */
    private TextView mContent;
    /**
     * 动画
     */
    private AnimationDrawable mAnimation;

    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingProgressDialog(@NonNull Context context, boolean cancelable, @Nullable
            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 构造方法
     *
     * @param context Context
     * @param content 标题
     * @param resId   动画资源
     */
    public LoadingProgressDialog(Context context, String content, int resId) {
        super(context,R.style.loadingDialog);
        this.mContext = context;
        this.mLoadingTitle = content;
        this.mResId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.layout_loading);
        mImage = (ImageView) findViewById(R.id.iv_loading);
        mContent = (TextView) findViewById(R.id.tv_loading);

        //点击其他地方不能取消
        setCanceledOnTouchOutside(false);

        //设置对话框的宽度为屏幕宽度的一般
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = DevicesUtil.getDeviceWidth(mContext).widthPixels / 2;
            window.setAttributes(attributes);
        }
    }

    private void initData() {
        //设置图片的资源
        mImage.setBackgroundResource(mResId);
        //获取图片背景
        mAnimation = (AnimationDrawable) mImage.getBackground();
        mImage.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        //设置标题
        mContent.setText(mLoadingTitle);
    }
}
