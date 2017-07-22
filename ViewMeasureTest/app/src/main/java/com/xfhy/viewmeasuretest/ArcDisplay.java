package com.xfhy.viewmeasuretest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by xfhy on 2017/3/28. 弧线展示图
 */

public class ArcDisplay extends View {

  /**
   * 绘制内部圆需要的成员变量
   */
  private Paint mCirclePaint;
  private float mCenter;
  private float mInsideRadius;
  /**
   * 绘制外面弧度所需要的成员变量
   */
  private Paint mArcPaint;
  private RectF mArcRectF;
  private float mSweepAngle = 90;
  /**
   * 绘制中间Text
   */
  private Paint mTextPaint;
  private String mShowText;
  private float mShowTextSize;
  private int mWidth;
  private int mHeight;

  public ArcDisplay(Context context) {
    super(context);
  }

  public ArcDisplay(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArcDisplay(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 绘制圆
    canvas.drawCircle(mCenter, mCenter, mInsideRadius, mCirclePaint);
    // 绘制弧线
    /**
     * oval：用于定义圆弧形状和大小的椭圆形边界
     startAngle float：弧开始的起始角度（以度为单位）
     sweepAngle float：顺时针测量的扫描角度（以度为单位）
     useCenter boolean：如果为true，请在圆弧中包含椭圆的中心，如果正在进行描边，则将其关闭。 这会画一个楔子
     paint：油漆用来画弧
     */
    canvas.drawArc(mArcRectF, -90, mSweepAngle, false, mArcPaint);
    // 绘制文字
    canvas.drawText(mShowText, 0, mShowText.length(),
        mCenter, mCenter + (mShowTextSize / 4), mTextPaint);
  }

  /**
   * 当这个视图的大小改变时，这在布局期间被调用。 如果您刚刚添加到视图层次结构中，则调用旧值0。
   * @param w
   * @param h
   * @param oldw
   * @param oldh
   */
  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = getMeasuredWidth();
    mHeight = getMeasuredHeight();
    this.initView();
  }

  /**
   * 初始化布局
   */
  private void initView() {
    float length = Math.min(mWidth,mHeight);

    mCenter = length/2;  //中间的圆的中点坐标
    mInsideRadius = (float) (length*0.5/2);
    mCirclePaint = new Paint();
    mCirclePaint.setAntiAlias(true);
    //getColor(int) api 23之后应该是被  int getColor (int id,Resources.Theme theme)代替
    mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));

    //绘制外层的弧
    mArcRectF = new RectF(
        (float) (length * 0.1), (float) (length * 0.1),
        (float) (length * 0.9), (float) (length * 0.9));

    mArcPaint = new Paint();
    mArcPaint.setAntiAlias(true);
    mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
    mArcPaint.setStrokeWidth((float) (length * 0.05));
    mArcPaint.setStyle(Paint.Style.STROKE);

        /*中间显示的文字*/
    mShowText = setShowText();
    mShowTextSize = setShowTextSize();
    mTextPaint = new Paint();
    mTextPaint.setTextSize(mShowTextSize);
    mTextPaint.setTextAlign(Paint.Align.CENTER);

  }

  private float setShowTextSize() {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());
  }

  private String setShowText() {
    return "Android Skill";
  }

  /**
   * 通知View进行重绘
   */
  public void forceInvalidate() {
    this.invalidate();
  }

  /**
   * 设置顺时针测量的扫描角度（以度为单位）
   * @param sweepValue
   */
  public void setSweepValue(float sweepValue) {
    if (sweepValue >= 0) {
      mSweepAngle = sweepValue;
    } else {
      mSweepAngle = 90;
    }
    this.invalidate();
  }

}
