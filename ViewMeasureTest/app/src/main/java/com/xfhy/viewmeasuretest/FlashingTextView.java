package com.xfhy.viewmeasuretest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xfhy on 2017/3/28.
 */

public class FlashingTextView extends TextView {

  private int mViewWidth = 0;
  private Paint mPaint;
  private LinearGradient mLinearGradient;
  private Matrix mGradientMatrix;
  private int mTranslate;

  public FlashingTextView(Context context) {
    super(context);
  }

  public FlashingTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FlashingTextView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (mViewWidth == 0) {
      mViewWidth = getMeasuredWidth();
      if (mViewWidth > 0) {
        mPaint = getPaint();
        mLinearGradient = new LinearGradient(
            0,
            0,
            mViewWidth,
            0,
            new int[] {
                Color.BLUE, 0xffffffff,
                Color.BLUE
            },
            null,
            Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();
      }
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mGradientMatrix != null) {
      mTranslate += mViewWidth/5;
      if(mTranslate>2*mViewWidth){
        mTranslate = -mViewWidth;
      }
      mGradientMatrix.setTranslate(mTranslate,0);
      mLinearGradient.setLocalMatrix(mGradientMatrix);
      postInvalidateDelayed(300);
    }
  }
}
