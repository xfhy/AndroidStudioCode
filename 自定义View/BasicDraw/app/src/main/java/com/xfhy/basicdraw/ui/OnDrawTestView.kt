package com.xfhy.basicdraw.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * author feiyang
 * time create at 2017/11/8 9:00
 * description
 */
class OnDrawTestView : View {

    /**
     * 尽量用val
     */
    private val paint = Paint()
    private val path = Path()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //抗锯齿
        paint.isAntiAlias = true
        //颜色
        paint.color = Color.BLUE
        // 设置绘制模式  FILL 是填充模式，STROKE 是画线模式（即勾边模式），FILL_AND_STROKE 是两种模式一并使用：既画线又填充。它的默认值是 FILL，填充模式。
        paint.style = Paint.Style.STROKE
        // 线条宽度   单位:像素
        paint.strokeWidth = 5f
        //字体大小
        paint.textSize = 45f

        //画圆               参数:圆心坐标x,y,半径(px),画笔
        //canvas?.drawCircle(200f, 200f, 100f, paint)

        //在整个绘制区域统一涂上指定的颜色。
        //这类颜色填充方法一般用于在绘制之前设置底色，或者在绘制之后为界面设置半透明蒙版。
        //canvas?.drawColor(Color.parseColor("#88880000"))
        //        canvas?.drawARGB(100, 100, 200, 100)

        //画矩形
        //paint.style = Paint.Style.FILL
        //canvas?.drawRect(100f, 100f, 500f, 500F, paint)


        //paint.style = Paint.Style.STROKE
        //canvas?.drawRect(700f, 100f, 1100f, 500f, paint)


        //画圆点
        /**
         * 注：Paint.setStrokeCap(cap) 可以设置点的形状，但这个方法并不是专门用来设置点的形状的，而是一个设置线条端点形状的方法。端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种，具体会在下一节里面讲。
         */
        /*paint.strokeWidth = 40f
        paint.strokeCap = Paint.Cap.SQUARE
        canvas?.drawPoint(200f, 200f, paint)

        //画方点
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawPoint(400f, 400f, paint)

        *//*
        * drawOval(float left, float top, float right, float bottom, Paint paint) 画椭圆
        * *//*
        //实心椭圆
        paint.style = Paint.Style.FILL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawOval(50f, 50f, 350f, 200f, paint)
        }

        //空心椭圆
        paint.style = Paint.Style.STROKE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawOval(400f, 50f, 700f, 200f, paint)
        }*/

        /*
        * 画线
        * */
        //canvas?.drawLine(0f, 0f, 500f, 500f, paint)

        /*
        * drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint)
        * left, top, right, bottom 是四条边的坐标，rx 和 ry 是圆角的横向半径和纵向半径。
        * 画圆角矩形
        *
        * */
        /*paint.style = Paint.Style.FILL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawRoundRect(100f, 100f, 200f, 200f, 20f, 10f, paint)
        }*/

        /*
        drawArc() 是使用一个椭圆来描述弧形的。
        left, top, right, bottom 描述的是这个弧形所在的椭圆；
        startAngle 是弧形的起始角度（
        x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度），
        sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心，
        如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
        * */
        //paint.style = Paint.Style.FILL
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawArc(200f, 200f, 400f, 400f, 0f, 90f, true, paint)
        }*/

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawArc(100f, 100f, 400f, 400f, 0f, 60f, false, paint)
        }

        paint.style = Paint.Style.STROKE // 画线模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawArc(200f, 100f, 800f, 500f, 180f, 60f, false, paint)
        } */// 绘制不封口的弧形

        /*
        * 到此为止，以上就是 Canvas 所有的简单图形的绘制。除了简单图形的绘制， Canvas 还可以使用  drawPath(Path path) 来绘制自定义图形。
        *
        * drawPath(Path path, Paint paint) 画自定义图形

        这个方法有点复杂，需要展开说一下。

        前面的这些方法，都是绘制某个给定的图形，而 drawPath() 可以绘制自定义图形。
        当你要绘制的图形比较特殊，使用前面的那些方法做不到的时候，就可以使用 drawPath() 来绘制。
        * */

        /*
        * 第一组： addXxx() ——添加子图形

addCircle(float x, float y, float radius, Direction dir) 添加圆
x, y, radius 这三个参数是圆的基本信息，最后一个参数 dir 是画圆的路径的方向。

路径方向有两种：顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise) 。对于普通情况，
这个参数填 CW 还是填 CCW 没有影响。它只是在需要填充图形 (Paint.Style 为 FILL 或 FILL_AND_STROKE) ，
并且图形出现自相交时，用于判断填充范围的。比如下面这个图形：


        * */
        /*paint.style = Paint.Style.FILL
        path.addCircle(400f, 400f, 100f, Path.Direction.CCW)
        path.addCircle(500f, 400f, 100f, Path.Direction.CCW)
        canvas?.drawPath(path, paint)*/

        /*
        * addOval(float left, float top, float right, float bottom, Direction dir) /
         * addOval(RectF oval, Direction dir) 添加椭圆

addRect(float left, float top, float right, float bottom, Direction dir) /
 addRect(RectF rect, Direction dir) 添加矩形

addRoundRect(RectF rect, float rx, float ry, Direction dir) /
addRoundRect(float left, float top, float right, float bottom, float rx, float ry, Direction dir) / addRoundRect(RectF rect, float[] radii, Direction dir) /
addRoundRect(float left, float top, float right, float bottom, float[] radii, Direction dir) 添加圆角矩形

addPath(Path path) 添加另一个 Path

上面这几个方法和 addCircle() 的使用都差不多，不再做过多介绍。
        * */

        /*paint.style = Paint.Style.STROKE
        path.lineTo(100f, 100f)
        path.moveTo(200f,100f)
        path.rLineTo(100f, 0f)
        canvas?.drawPath(path, paint)*/

        /*
        * quadTo(float x1, float y1, float x2, float y2) / rQuadTo(float dx1, float dy1, float dx2, float dy2) 画二次贝塞尔曲线
        * 这条二次贝塞尔曲线的起点就是当前位置，而参数中的 x1, y1 和 x2, y2 则分别是控制点和终点的坐标。和 rLineTo(x, y) 同理，rQuadTo(dx1, dy1, dx2, dy2) 的参数也是相对坐标

贝塞尔曲线：贝塞尔曲线是几何上的一种曲线。它通过起点、控制点和终点来描述一条曲线，主要用于计算机图形学。概念总是说着容易听着难，总之使用它可以绘制很多圆润又好看的图形，但要把它熟练掌握、灵活使用却是不容易的。不过还好的是，一般情况下，贝塞尔曲线并没有什么用处，只在少数场景下绘制一些特殊图形的时候才会用到，所以如果你还没掌握自定义绘制，可以先把贝塞尔曲线放一放，稍后再学也完全没问题。至于怎么学，贝塞尔曲线的知识网上一搜一大把，我这里就不讲了。
cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) / rCubicTo(float x1, float y1, float x2, float y2, float x3, float y3) 画三次贝塞尔曲线

和上面这个 quadTo() rQuadTo() 的二次贝塞尔曲线同理，cubicTo() 和 rCubicTo() 是三次贝塞尔曲线，不再解释。
        * */
        //path.quadTo(200f, 200f, 400f, 400f)
        //canvas?.drawPath(path, paint)

        /*
        * moveTo(float x, float y) / rMoveTo(float x, float y) 移动到目标位置
        不论是直线还是贝塞尔曲线，都是以当前位置作为起点，而不能指定起点。
        但你可以通过 moveTo(x, y) 或  rMoveTo() 来改变当前位置，从而间接地设置这些方法的起点。
        * */


        /*
        * 另外，第二组还有两个特殊的方法： arcTo() 和 addArc()。它们也是用来画线的，但并不使用当前位置作为弧线的起点。

arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo) / arcTo(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo) / arcTo(RectF oval, float startAngle, float sweepAngle) 画弧形

这个方法和 Canvas.drawArc() 比起来，少了一个参数 useCenter，而多了一个参数 forceMoveTo 。

少了 useCenter ，是因为 arcTo() 只用来画弧形而不画扇形，所以不再需要 useCenter 参数；而多出来的这个 forceMoveTo 参数的意思是，绘制是要「抬一下笔移动过去」，还是「直接拖着笔过去」，区别在于是否留下移动的痕迹。
        * */
        /* paint.style = Paint.Style.STROKE
         path.lineTo(100f, 100f)
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             // 强制移动到弧形起点（无痕迹）
             path.arcTo(100f, 100f, 300f, 300f, -90f, 90f, true)
         }
         canvas?.drawPath(path, paint)*/

        /*paint.style = Paint.Style.STROKE
        path.lineTo(100f,100f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.arcTo(100f,100f,300f,300f,-90f,90f,false)
        }
        canvas?.drawPath(path, paint)*/

        /*
        * addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) / addArc(RectF oval, float startAngle, float sweepAngle)

又是一个弧形的方法。一个叫 arcTo ，一个叫 addArc()，都是弧形，区别在哪里？其实很简单： addArc() 只是一个直接使用了 forceMoveTo = true 的简化版 arcTo() 。
        * */
        /*paint.style = Paint.Style.STROKE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addArc(100f, 100f, 300f, 300f, 90f, 90f)
        }
        canvas?.drawPath(path, paint)   */

        /*
        * close() 封闭当前子图形

它的作用是把当前的子图形封闭，即由当前位置向当前子图形的起点绘制一条直线。
        * */
        /*paint.style = Paint.Style.STROKE
        path.moveTo(100f, 100f)
        path.lineTo(200f, 100f)
        path.lineTo(150f, 150f)
        path.close()
        canvas?.drawPath(path, paint)*/

        /*
        * Path 方法第二类：辅助的设置或计算
        * */
        paint.style = Paint.Style.FILL
        path.addCircle(400f, 400f, 100f, Path.Direction.CW)
        path.addCircle(500f, 400f, 100f, Path.Direction.CW)
        path.fillType = Path.FillType.EVEN_ODD
        canvas?.drawPath(path, paint)

    }

}