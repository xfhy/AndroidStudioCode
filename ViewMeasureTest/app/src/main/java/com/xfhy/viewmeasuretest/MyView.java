package com.xfhy.viewmeasuretest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xfhy on 2017/3/25.
 */

public class MyView extends View {

  public MyView(Context context) {
    super(context);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /*
  * 重写onMeasure()方法的目的,就是为了能够给View一个wrap_content属性下的默认大小
  * */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setTextSize(30);
    canvas.drawText("啊哈", 0, 50, paint);
  }

  /**
   * 测量View的长度
   */
  private int measureHeight(int measureSpec) {
    int result = 0;

    //获取具体的测量模式和大小
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);

    if (specMode == MeasureSpec.EXACTLY) { //默认的大小(指定了具体的值  比如android:layout_width=100dp)
      result = measureSpec;
    } else {
      result = 200;
      if (specMode == MeasureSpec.AT_MOST) { //wrap_content
        result = Math.min(result, specSize);
      }
    }

    return result;
  }

  /**
   * 测量View的宽度
   */
  private int measureWidth(int measureSpec) {
    int result = 0;
    int specMode = MeasureSpec.getMode(measureSpec);   //获取具体的测量模式(EXACTLY,AT_MOST,UNSPECIFIED)
    int specSize = MeasureSpec.getSize(measureSpec); //获取具体的测量大小

    if (specMode == MeasureSpec.EXACTLY) {   //默认模式
      result = measureSpec;
    } else {
      result = 200;   //给一个默认的大小
      if (specMode == MeasureSpec.AT_MOST) {   //如果是wrap_content
        result = Math.min(result, specSize);   //取最小的  适合控件大小的
      }
    }

    return result;
  }
}
