package com.xfhy.viewmeasuretest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xfhy on 2017/3/28.
 */

public class Audiobar extends View {

  private int mWidth;
  private int mRectWidth;
  /**
   * 音频条的最大高度
   */
  private int mMaxRectHeight;
  private Paint mPaint;
  private int mRectCount = 12;

  /**
   * 每个音频条之间的偏移量
   */
  private int mOffset;

  public Audiobar(Context context) {
    super(context);
  }

  public Audiobar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Audiobar(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = getMeasuredWidth();
    mMaxRectHeight = getHeight();
  }

  private void initView() {
    mPaint = new Paint();
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int offset = 2;
    int currentHeight = 10;
    int mRectHeight = 10;
    for (int i = 0; i < mRectCount; i++) {
    }
  }
}
