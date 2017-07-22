package com.xfhy.viewmeasuretest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by xfhy on 2017/3/28.
 *
 * 创建复合控件 自定义的一个标题栏   可以复用,很方便
 * 还定义了点击事件响应   还可以设置左右两个按钮的可见性
 */

public class TopBar extends RelativeLayout{

  private int mLeftTextColor;
  private Drawable mLeftBackground;
  private String mLeftText;
  private int mRightTextColor;
  private Drawable mRightBackground;
  private String mRightText;
  private String mTitle;
  private float mTitleTextSize;
  private int mTitleTextColor;
  private LayoutParams mLeftLayoutParams;
  private LayoutParams mRightLayoutParams;
  private LayoutParams mTitleLayoutParams;
  topbarClickListener mListener = null;
  private Button mLeftButton;
  private Button mRightButton;
  private TextView mTitleView;

  public TopBar(Context context) {
    super(context);

  }

  public TopBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    // 通过这个方法，将你在attrs.xml中定义的declare-styleable的所有属性值存储到TypedArray中
    TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TopBar);

    // 从TypedArray中取出对应的值来为要设置的属性赋值
    mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
    mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
    mLeftText = ta.getString(R.styleable.TopBar_leftText);
    mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
    mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
    mRightText = ta.getString(R.styleable.TopBar_rightText);
    mTitle = ta.getString(R.styleable.TopBar__title);
    mTitleTextSize = ta.getDimension(R.styleable.TopBar__titleTextSize, 0);
    mTitleTextColor = ta.getColor(R.styleable.TopBar__titleTextColor, 0);

    //获取完TypedArray的值后,一般要调用recycle方法来避免重新创建的时候的错误
    ta.recycle();

    mLeftButton = new Button(context);
    mRightButton = new Button(context);
    mTitleView = new TextView(context);

    //为创建的组件元素赋值
    //值就来源于我们在引用的xml文件中给对应属性的赋值
    mLeftButton.setTextColor(mLeftTextColor);
    mLeftButton.setBackground(mLeftBackground);
    mLeftButton.setText(mLeftText);

    mRightButton.setTextColor(mRightTextColor);
    mRightButton.setBackground(mRightBackground);
    mRightButton.setText(mRightText);

    mTitleView.setText(mTitle);
    mTitleView.setTextColor(mTitleTextColor);
    mTitleView.setTextSize(mTitleTextSize);
    mTitleView.setGravity(Gravity.CENTER);

    //为组件元素设置相应的布局元素
    mLeftLayoutParams =
        new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
    //添加到ViewGroup
    addView(mLeftButton,mLeftLayoutParams);

    mRightLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
    addView(mRightButton,mRightLayoutParams);

    mTitleLayoutParams =
        new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    mTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
    addView(mTitleView, mTitleLayoutParams);

    /**
     * 按钮的点击事件,不需要具体的实现
     * 只需调用接口的方法,回调的时候,会有具体的实现
     */
    mRightButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if(mListener != null){
          mListener.rightClick();
        }
      }
    });

    mLeftButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if(mListener != null){
          mListener.leftClick();
        }
      }
    });

  }

  public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * 接口对象,实现回调机制,在回调方法中
   * 通过映射的接口对象调用接口中的方法
   * 而不用去考虑如何实现,具体的实现由调用者去创建
   */
  public interface topbarClickListener {
    //左按钮点击事件
    void leftClick();

    //右按钮点击事件
    void rightClick();
  }

  public void setOnTopbarClickListener(topbarClickListener mListener){
    this.mListener = mListener;
  }

  /**
   *  设置按钮的显示是否    通过id区分按钮   flag区分是否显示
   *
   * @param id   id
   * @param flag  是否显示
   */
  public void setButtonVisable(int id,boolean flag) {
    if(flag) {
      if(id == 0) {
        mLeftButton.setVisibility(View.VISIBLE);
      } else {
        mRightButton.setVisibility(View.VISIBLE);
      }
    } else {
      if(id == 0) {
        mLeftButton.setVisibility(View.GONE);
      } else {
        mRightButton.setVisibility(View.GONE);
      }
    }
  }

}
