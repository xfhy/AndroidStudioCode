package com.xfhy.canvasoperation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author feiyang
 * @time create at 2017/11/3 16:54
 * @description
 */
public class TaiChiView extends View {

    private Paint mWhitePaint = new Paint();
    private Paint mBlackPaint = new Paint();
    private int mWidth;
    private int mHeight;

    public TaiChiView(Context context) {
        this(context, null);
    }

    public TaiChiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TaiChiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setAntiAlias(true);

        mBlackPaint.setColor(Color.BLACK);
        mBlackPaint.setStyle(Paint.Style.FILL);
        mBlackPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //将画布移动到中心
        Point point = new Point(mWidth / 2, mHeight / 2);
        canvas.translate(point.x, point.y);

        //在绘制完背景色后调用即可
        canvas.rotate(degrees);

        //canvas.drawColor(Color.GRAY);  //背景色


        //绘制2个半圆
        RectF rectF = new RectF(-200, -200, 200, 200);
        canvas.drawArc(rectF, -90, 180, true, mWhitePaint);
        canvas.drawArc(rectF, 90, 180, true, mBlackPaint);

        // 绘制2个小圆
        canvas.drawCircle(0,-100,100,mBlackPaint);
        canvas.drawCircle(0,100,100,mWhitePaint);

        // 绘制更小的圆点
        canvas.drawCircle(0, -100, 20, mWhitePaint);
        canvas.drawCircle(0, 100, 20, mBlackPaint);

    }

    private float degrees = 0;          //旋转角度

    public void setRotate(float degrees) {
        this.degrees = degrees;
        invalidate();                   //重绘界面
    }


}
