package com.xfhy.filterkeyview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.xfhy.filterkeyview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xfhy
 *         create at 2017/12/3
 *         description：评分自定义LinearLayout
 */
public class HotelScoreView extends LinearLayout implements CompoundButton
        .OnCheckedChangeListener, View.OnClickListener {

    private CheckBox mNoLimitScoreCb;
    private CheckBox mOneScoreCb;
    private CheckBox mTwoScoreCb;
    private CheckBox mThreeScoreCb;
    private CheckBox mFourScoreCb;
    private Button mClearSelectedBtn;
    private Button mDetermineBtn;
    private List<String> mSelectedDatas = new ArrayList<>();
    private OnScoreDataChangeListener mDataChangeListener;

    public HotelScoreView(Context context) {
        this(context, null);
    }

    public HotelScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotelScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.hotel_layout_score_view, this, true);

        mNoLimitScoreCb = (CheckBox) findViewById(R.id.cb_score_no_limit);
        mOneScoreCb = (CheckBox) findViewById(R.id.cb_score_one);
        mTwoScoreCb = (CheckBox) findViewById(R.id.cb_score_two);
        mThreeScoreCb = (CheckBox) findViewById(R.id.cb_score_three);
        mFourScoreCb = (CheckBox) findViewById(R.id.cb_score_four);
        mClearSelectedBtn = (Button) findViewById(R.id.btn_clear_selection);
        mDetermineBtn = (Button) findViewById(R.id.btn_determine);


        mNoLimitScoreCb.setOnCheckedChangeListener(this);
        mOneScoreCb.setOnCheckedChangeListener(this);
        mTwoScoreCb.setOnCheckedChangeListener(this);
        mThreeScoreCb.setOnCheckedChangeListener(this);
        mFourScoreCb.setOnCheckedChangeListener(this);

        mClearSelectedBtn.setOnClickListener(this);
        mDetermineBtn.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkAllStarUnChecked()) {
            return;
        }
        int viewId = buttonView.getId();
        if (viewId == R.id.cb_score_no_limit && isChecked) {
            //不限 评分
            starNoLimitChecked();
        } else if (viewId == R.id.cb_score_one && isChecked) {
            // 1分以上
            oneScoreChecked();
        } else if (viewId == R.id.cb_score_two && isChecked) {
            // 2分以上
            twoScoreChecked();
        } else if (viewId == R.id.cb_score_three && isChecked) {
            // 3分以上
            threeScoreChecked();
        } else if (viewId == R.id.cb_score_four && isChecked) {
            // 4分以上
            fourScoreChecked();
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_clear_selection) {
            //清空选择
            clearSelected();
        } else if (viewId == R.id.btn_determine) {
            //确定选择
            determineSelected();
        }
    }

    /**
     * 检查是否全部都未选中
     */
    private boolean checkAllStarUnChecked() {
        if (!mNoLimitScoreCb.isChecked() && !mOneScoreCb.isChecked() && !mTwoScoreCb.isChecked() &&
                !mThreeScoreCb.isChecked() && !mFourScoreCb.isChecked()) {
            //全部未选中 则是不限
            mNoLimitScoreCb.setChecked(true);
            return true;
        }
        return false;
    }

    /**
     * 不限
     */
    private void starNoLimitChecked() {
        mNoLimitScoreCb.setChecked(true);
        mOneScoreCb.setChecked(false);
        mTwoScoreCb.setChecked(false);
        mThreeScoreCb.setChecked(false);
        mFourScoreCb.setChecked(false);
    }

    private void oneScoreChecked() {
        mOneScoreCb.setChecked(true);
        checkIsNoLimit();
    }

    private void twoScoreChecked() {
        mTwoScoreCb.setChecked(true);
        checkIsNoLimit();
    }

    private void threeScoreChecked() {
        mThreeScoreCb.setChecked(true);
        checkIsNoLimit();
    }

    private void fourScoreChecked() {
        mFourScoreCb.setChecked(true);
        checkIsNoLimit();
    }

    private void clearSelected() {
        starNoLimitChecked();
    }

    private void determineSelected() {
        mSelectedDatas.clear();
        //如果选择了不限,那么不返回任何数据
        if (mNoLimitScoreCb.isChecked()) {
            return;
        }
        if (mOneScoreCb.isChecked()) {
            mSelectedDatas.add("1分以上");
        }
        if (mTwoScoreCb.isChecked()) {
            mSelectedDatas.add("2分以上");
        }
        if (mThreeScoreCb.isChecked()) {
            mSelectedDatas.add("3分以上");
        }
        if (mFourScoreCb.isChecked()) {
            mSelectedDatas.add("4分以上");
        }
        if (mDataChangeListener != null) {
            mDataChangeListener.onChange(mSelectedDatas);
        }
    }

    /**
     * 检查是否满足不限星级条件
     */
    private void checkIsNoLimit() {
        //全部选中 则是不限
        if (mOneScoreCb.isChecked() && mTwoScoreCb.isChecked() && mThreeScoreCb.isChecked()
                && mFourScoreCb.isChecked()) {
            starNoLimitChecked();
        } else {
            mNoLimitScoreCb.setChecked(false);
        }

    }

    /**
     * 设置评分监听器
     */
    public void setOnDataChangeListener(OnScoreDataChangeListener dataChangeListener) {
        this.mDataChangeListener = dataChangeListener;
    }

    /**
     * 选择变化监听器
     */
    public interface OnScoreDataChangeListener {
        /**
         * 将用户选择的数据进行返回   将每个选择的分数也保存至选择好的数据中
         */
        void onChange(List<String> keyList);
    }

}
