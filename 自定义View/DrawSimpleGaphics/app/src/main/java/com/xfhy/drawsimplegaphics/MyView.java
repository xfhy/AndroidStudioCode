package com.xfhy.drawsimplegaphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by XFHY on 2016/10/25.
 * 如果要进行图形的绘制,则可以直接用一个类继承View类,之后覆写View类中的指定方法.
 * onDraw() 在此方法中实现绘图的操作
 * onDrawScrollBars() 实现水平或垂直滚动条的绘图操作
 *
 * 一般情况覆写onDraw()即可,可是要想完成绘图操作,还需要掌握4个核心的操作类
 * Bitmap:主要是表示图片,所包含的图片可以来自文件或由程序创建
 * Paint:主要的绘图工具类,可以指定绘图的样式
 * Canvas:是一个操作绘图以及Bitmap的平台,相当于提供了一个画板的功能,在onDraw()方法的参数中也定义了
 * 次类型的参数,可以依靠次类完成具体的绘图操作.
 * Drawable:绘制图形的公共父类,可以绘制各种图形,图层等.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //alt + insert  快速实现父类的构造方法

    //alt+enter 这个是Android Studio神快捷键。如果你还认为Alt＋Enter键是导入包，那就大错特错了。
    // 以后有事没事就按下吧。它会根据不同的情况给出操作建议，大大提高工作效率。


    @Override
    protected void onDraw(Canvas canvas) {    //覆盖绘图的方法
        //drawMyElementarySchoolFigure(canvas);
        drawMyBitmap(canvas);

    }


    /**
     * 绘制基本图形
     * @param canvas  画板
     */
    private void drawMyElementarySchoolFigure(Canvas canvas){
        canvas.drawColor(Color.WHITE);      //设置背景颜色
        Paint paint = new Paint();          //定义Paint对象
        paint.setColor(Color.BLUE);         //设置为蓝色显示
        canvas.drawCircle(30,50,25,paint);  //画圆

        paint.setColor(Color.BLACK);        //设置为黑色显示
        canvas.drawRect(80,20,300,80,paint);//画矩形

        Rect rect = new Rect();
        rect.set(180,20,300,80);
        paint.setStyle(Paint.Style.STROKE);   //空心显示
        canvas.drawRect(rect,paint);

        paint.setColor(Color.RED);
        paint.setTextSize(20);  //设置字体大小
        canvas.drawText("哈哈哈哈哈",10,110,paint);   //显示文字

        paint.setColor(Color.BLACK);
        canvas.drawLine(10,120,300,120,paint);    //画线

        RectF oval = new RectF();      //定义参考矩形
        oval.set(10.0f,140.0f,110.0f,200.0f);
        canvas.drawOval(oval,paint);  //画椭圆

        oval = new RectF();
        oval.set(150.0f,140.0f,210.0f,200.0f);
        canvas.drawArc(oval,150.0f,140.0f,true,paint);  //画弧
    }

    /**
     * 从指定资源中读取指定的图片,再利用Canvas类在屏幕中将图片绘制出来
     * 从资源文件中取得一个Bitmap实例
     * @param canvas
     */
    public void drawMyBitmap(Canvas canvas){
        /*----------------从资源文件中取得一个Bitmap实例--------------------------*/
        //从指定资源中读取指定的图片,再利用Canvas类在屏幕中将图片绘制出来
        //需要一个BitmapFactory类的支持
        Bitmap bitmap = BitmapFactory.decodeResource(super.getResources(),
                R.drawable.black_xfhy);
        bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);

        Paint paint = new Paint();
        paint.setAntiAlias(true);             //消除锯齿
        canvas.drawBitmap(bitmap, 0, 0, paint);  //画图
        paint.setColor(Color.BLUE);     //字体颜色
        paint.setTextSize(20);           //定义字号
        canvas.drawText("图片高度: "+bitmap.getHeight()+", 图片宽度: "+bitmap.getWidth(),
                10,bitmap.getHeight()+20,paint);   //输出文字
    }

}
