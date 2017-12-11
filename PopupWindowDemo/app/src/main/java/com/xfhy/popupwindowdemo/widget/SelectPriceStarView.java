package com.xfhy.popupwindowdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.popupwindowdemo.R;

import java.util.Locale;

/**
 * Created by feiyang on 2017/12/4 09:12
 * Description : 自定义LinearLayout 用于选择价格/星级
 * 搞一个model.保存当前状态,    记得备份百度云
 */
public class SelectPriceStarView extends LinearLayout implements RangeSeekBar
        .OnRangeChangedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "SelectPriceStarView";

    private static final int MAX_PRICE = 1000;

    private TextView mSelectedPriceTv;
    private LinearLayout mPriceLevelLayout;
    private RangeSeekBar mChangePriceRsb;
    private CheckBox mNoLimitStarCb;
    private CheckBox mTwoStarCb;
    private CheckBox mThreeStarCb;
    private CheckBox mFourStarCb;
    private CheckBox mFiveStarCb;
    private Button mClearSelectedBtn;
    private Button mDetermineBtn;

    public SelectPriceStarView(Context context) {
        this(context, null);
    }

    public SelectPriceStarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectPriceStarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_select_price_star, this, true);

        mSelectedPriceTv = (TextView) findViewById(R.id.tv_selected_price);
        mPriceLevelLayout = (LinearLayout) findViewById(R.id.ll_hotel_price_level);
        mChangePriceRsb = (RangeSeekBar) findViewById(R.id.rsb_change_price);
        mNoLimitStarCb = (CheckBox) findViewById(R.id.cb_star_no_limit);
        mTwoStarCb = (CheckBox) findViewById(R.id.cb_star_two);
        mThreeStarCb = (CheckBox) findViewById(R.id.cb_star_three);
        mFourStarCb = (CheckBox) findViewById(R.id.cb_star_four);
        mFiveStarCb = (CheckBox) findViewById(R.id.cb_star_five);
        mClearSelectedBtn = (Button) findViewById(R.id.btn_clear_selection);
        mDetermineBtn = (Button) findViewById(R.id.btn_determine);

        mChangePriceRsb.setOnRangeChangedListener(this);

        mNoLimitStarCb.setOnCheckedChangeListener(this);
        mTwoStarCb.setOnCheckedChangeListener(this);
        mThreeStarCb.setOnCheckedChangeListener(this);
        mFourStarCb.setOnCheckedChangeListener(this);
        mFiveStarCb.setOnCheckedChangeListener(this);

        mClearSelectedBtn.setOnClickListener(this);
        mDetermineBtn.setOnClickListener(this);
    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float min, float max) {
        Log.d(TAG, "onRangeChanged: min=" + min + "    max=" + max);
        int minPrice = (int) min;
        int maxPrice = (int) max;
        mSelectedPriceTv.setText(String.format(Locale.CHINA, "¥%s - %s", minPrice,
                maxPrice == MAX_PRICE ? "不限" : "¥" + maxPrice));
        checkRangePriceView(minPrice, maxPrice);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkAllStarUnChecked()) {
            return;
        }
        int viewId = buttonView.getId();
        if (viewId == R.id.cb_star_no_limit && isChecked) {
            //不限 星级
            starNoLimitChecked();
        } else if (viewId == R.id.cb_star_two && isChecked) {
            // 二星
            twoStarChecked();
        } else if (viewId == R.id.cb_star_three && isChecked) {
            // 三星
            threeStarChecked();
        } else if (viewId == R.id.cb_star_four && isChecked) {
            // 四星
            fourStarChecked();
        } else if (viewId == R.id.cb_star_five && isChecked) {
            // 五星
            fiveStarChecked();
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Log.d(TAG, "onClick: v.getId()=" + v.getId());
        if (viewId == R.id.btn_clear_selection) {
            //清空选择
            clearSelected();
        } else if (viewId == R.id.btn_determine) {
            //确定选择
            determineSelected();
        }
    }

    /**
     * 检查价格文字栏哪些文字需要显示为灰色
     *
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     */
    private void checkRangePriceView(int minPrice, int maxPrice) {
        int childCount = mPriceLevelLayout.getChildCount();
        if (childCount < 1) {
            return;
        }
        //思想:将价格均分,判断哪些在minPrice-maxPrice之间,在之间的设置为橙色,不在这之间的设置为灰色
        int[] cells = new int[childCount];
        int oneCell = MAX_PRICE / (childCount - 1);
        int cellsLength = cells.length;
        cells[0] = 0;
        for (int i = 1; i < cellsLength; i++) {
            cells[i] = oneCell + cells[i - 1];
        }

        for (int i = 0; i < cellsLength; i++) {
            View childAt = mPriceLevelLayout.getChildAt(i);
            if (cells[i] < minPrice || cells[i] > maxPrice) {
                childAt.setEnabled(false);
            } else if (cells[i] >= minPrice && cells[i] <= maxPrice) {
                childAt.setEnabled(true);
            }
        }
    }

    /**
     * 检查是否全部都未选中
     */
    private boolean checkAllStarUnChecked() {
        if (!mNoLimitStarCb.isChecked() && !mTwoStarCb.isChecked() && !mThreeStarCb.isChecked() &&
                !mFourStarCb.isChecked() && !mFiveStarCb.isChecked()) {
            //全部未选中 则是不限
            mNoLimitStarCb.setChecked(true);
            return true;
        }
        return false;
    }

    /**
     * 不限
     */
    private void starNoLimitChecked() {
        mNoLimitStarCb.setChecked(true);
        mTwoStarCb.setChecked(false);
        mThreeStarCb.setChecked(false);
        mFourStarCb.setChecked(false);
        mFiveStarCb.setChecked(false);
    }

    private void twoStarChecked() {
        mTwoStarCb.setChecked(true);
        checkIsNoLimit();
    }

    private void threeStarChecked() {
        mThreeStarCb.setChecked(true);
        checkIsNoLimit();
    }

    private void fourStarChecked() {
        mFourStarCb.setChecked(true);
        checkIsNoLimit();
    }

    private void fiveStarChecked() {
        mFiveStarCb.setChecked(true);
        checkIsNoLimit();
    }

    private void clearSelected() {
        mChangePriceRsb.setInitSelectedValue(0.0f, 1.0f);
        starNoLimitChecked();
    }

    private void determineSelected() {

    }

    /**
     * 检查是否满足不限星级条件
     */
    private void checkIsNoLimit() {
        //全部选中 则是不限
        if (mTwoStarCb.isChecked() && mThreeStarCb.isChecked() && mFourStarCb.isChecked()
                && mFiveStarCb.isChecked()) {
            starNoLimitChecked();
        } else {
            mNoLimitStarCb.setChecked(false);
        }

    }



}
