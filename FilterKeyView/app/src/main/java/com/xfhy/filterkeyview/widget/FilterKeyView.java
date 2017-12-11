package com.xfhy.filterkeyview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.xfhy.filterkeyview.PixelUtil;
import com.xfhy.filterkeyview.R;
import com.xfhy.filterkeyview.widget.model.FilterKeyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiyang on 2017/12/3 16:56
 * Description : 筛选关键词 自定义ScrollView
 */
public class FilterKeyView extends HorizontalScrollView implements CompoundButton
        .OnCheckedChangeListener {
    private static final String TAG = "FilterKeyView";
    /**
     * 关键词集合
     */
    private List<FilterKeyModel> mKeyList = new ArrayList<>();
    private LinearLayout mKeyLayout;
    private OnChangeStateChangeListener mStateChangeListener;

    public FilterKeyView(Context context) {
        this(context, null);
    }

    public FilterKeyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.hotel_layout_filter_key, this, true);
        mKeyLayout = (LinearLayout) findViewById(R.id.ll_filter_key_container);
    }

    /**
     * 初始化数据
     */
    public void initKeyList(List<FilterKeyModel> keyList) {
        if (keyList == null) {
            return;
        }
        mKeyList.clear();
        mKeyList.addAll(keyList);
        addCheckBoxToLayout();
    }


    private void addCheckBoxToLayout() {
        mKeyLayout.removeAllViews();
        //需要一个model   用来传入所需数据  mKeyList不能是装String,应该是model
        //里面需要存放当前项名称,当前项是否可以展开,

        int keyListSize = mKeyList.size();
        for (int i = 0; i < keyListSize; i++) {
            FilterKeyModel filterKeyModel = mKeyList.get(i);
            CheckBox checkBox = new CheckBox(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = PixelUtil.dp2px(getContext(), 3);
            layoutParams.rightMargin = PixelUtil.dp2px(getContext(), 3);
            checkBox.setLayoutParams(layoutParams);
            checkBox.setText(filterKeyModel.keyName);
            checkBox.setTextColor(getContext().getResources().getColor(R.color.color_1e1e1e));
            checkBox.setButtonDrawable(null);
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setEllipsize(TextUtils.TruncateAt.END);
            checkBox.setOnCheckedChangeListener(this);
            //标识该CheckBox
            checkBox.setTag(i);
            if (filterKeyModel.canExpand) {
                //这里还是像下面那样用图片吧,下面这种效果不好
                //TODO 找UI拿图片
                checkBox.setBackground(getContext().getResources().getDrawable(R.drawable
                        .gray_border_solid_uncheck));
                Drawable drawable = getResources().getDrawable(R.drawable.blue_arrow_down);
                drawable.setBounds(PixelUtil.dp2px(getContext(), -5), 0, drawable
                        .getIntrinsicWidth(), drawable.getIntrinsicWidth());
                checkBox.setCompoundDrawables(null, null, drawable, null);
            } else {
                checkBox.setBackground(getContext().getResources().getDrawable(R.drawable
                        .public_passenger_checkbox_selector));
            }
            mKeyLayout.addView(checkBox);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewPosition = (int) buttonView.getTag();
        boolean listenerIsNull = (mStateChangeListener == null);
        if (mKeyList.size() > viewPosition) {
            FilterKeyModel filterKeyModel = mKeyList.get(viewPosition);

            //原来的状态是关闭  则 展开  ,原来是展开->则关闭
            if (filterKeyModel.canExpand) {
                if (filterKeyModel.isExpand && !listenerIsNull) {
                    mStateChangeListener.onPutAway(viewPosition);
                    Log.w(TAG, "onCheckedChanged: 收起");
                    filterKeyModel.isExpand = !filterKeyModel.isExpand;
                } else if (!filterKeyModel.isExpand && !listenerIsNull) {
                    mStateChangeListener.onExpand(viewPosition);
                    Log.w(TAG, "onCheckedChanged: 展开");
                    filterKeyModel.isExpand = !filterKeyModel.isExpand;
                }

            }
            filterKeyModel.isChecked = isChecked;
            Log.w(TAG, "onCheckedChanged: isChecked:" + isChecked);
            if (!listenerIsNull) {
                mStateChangeListener.onChange(mKeyList);
            }
        }
    }

    /**
     * 设置哪一项被收起(当用户点击PopupWindow外部时需要调用)
     *
     * @param position 索引
     */
    public void setPutAway(int position) {
        if (mKeyList.size() > position && mKeyList.get(position).canExpand) {
            mKeyList.get(position).isExpand = false;
        }
    }

    public void setOnStateChangeListener(OnChangeStateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
    }

    /**
     * kay选择状态改变监听器
     */
    public interface OnChangeStateChangeListener {
        /**
         * 返回所有状态数据
         */
        void onChange(List<FilterKeyModel> keyList);

        /**
         * 展开position索引处
         */
        void onExpand(int position);

        /**
         * 收缩position索引处
         */
        void onPutAway(int position);
    }


}
