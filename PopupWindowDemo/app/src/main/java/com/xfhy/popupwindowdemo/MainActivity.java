package com.xfhy.popupwindowdemo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xfhy.popupwindowdemo.popup.PopupWindowHelper;
import com.xfhy.popupwindowdemo.widget.HotelPriceView;
import com.xfhy.popupwindowdemo.widget.SelectPriceStarView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button popupBtn;
    private LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popupBtn = (Button) findViewById(R.id.btn_test);
        mRootView = (LinearLayout) findViewById(R.id.ll_root);
        HotelPriceView hotelPriceView = (HotelPriceView) findViewById(R.id.hpv_test);
        hotelPriceView.setSelected(false);

        popupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出PopupWindow
                showUpPop(v);
            }
        });
    }

    //向上弹出
    public void showUpPop(View view) {
        SelectPriceStarView selectPriceStarView = new SelectPriceStarView(this);
        PopupWindowHelper.Builder builder = new PopupWindowHelper.Builder();
        builder.context(this).view(selectPriceStarView).anchor(popupBtn).alpha
                (0.8f).width(getWidth())
                .height(getHeight() / 2).backgroundDrawable(new ColorDrawable()).focusable(true)
                .position(PopupWindowHelper.SCREEN_BOTTOM);
        builder.build().show();
        Log.i(TAG, "showUpPop: getHeight() / 2 =  " + getHeight() / 2);
    }

    private int getHeight() {
        WindowManager systemService = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private int getWidth() {
        WindowManager systemService = (WindowManager) getSystemService(Context
                .WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
