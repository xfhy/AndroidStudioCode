package com.xfhy.basicdraw.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.xfhy.basicdraw.entity.AbilityBean
import android.util.Log


/**
 * author feiyang
 * time create at 2017/11/10 13:31
 * description 仿制LOL能力图
 */
class AbilityView : View {

    /**
     * 几个能力
     */
    var abilityCount = 7
    var mData: AbilityBean? = null
        set(value) {
            invalidate()
        }
    private val mLinePaint = Paint()
    private val mTextPaint = Paint()

    var mWidth = 0
    var mHeight = 0

    /**
     * 间隔数量，就把半径分为几段
     */
    var mIntervalCount = 4
    /**
     * 2个顶点之间的夹角
     */
    var mAngle: Float = 0.0f
    /**
     * 最外圈的半径
     */
    var mBigRadius = 0
    /**
     * 存储多边形顶点数组的数组
     */
    var pointsArrayList: ArrayList<ArrayList<PointF>>? = null
    /**
     * 中心
     */
    var centerPoint = PointF()
    /**
     * 每个小圈的厚度
     */
    var mBar = 0

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        initPaint()

        mAngle = (2 * Math.PI / abilityCount).toFloat()
    }

    private fun initPaint() {
        mTextPaint.textSize = 20f
        mLinePaint.style = Paint.Style.FILL_AND_STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //将画布移动到中心点
        canvas?.translate(centerPoint.x, centerPoint.y)

        drawPolygon(canvas)
    }

    /**
     * 画圈圈
     */
    private fun drawPolygon(canvas: Canvas?) {
        canvas?.save()//保存画布当前状态(平移、放缩、旋转、裁剪等),和canvas.restore()配合使用
        mLinePaint.style = Paint.Style.FILL_AND_STROKE
        Log.e("xfhy", "drawPolygon")

        val outPointSize = pointsArrayList?.size
        val path = Path()
        for (i in 0 until outPointSize!!) {

            //每一层的颜色都不同
            when (i) {
                0 -> mLinePaint.color = Color.parseColor("#D4F0F3")
                1 -> mLinePaint.color = Color.parseColor("#99DCE2")
                2 -> mLinePaint.color = Color.parseColor("#56C1C7")
                3 -> mLinePaint.color = Color.parseColor("#278891")
            }

            val inPoint = pointsArrayList?.get(i)
            val inPointSize = inPoint?.size
            for (j in 0 until inPointSize!!) {
                if (j == 0) {
                    path.moveTo(inPoint[j].x, inPoint[j].y)
                } else {
                    path.lineTo(inPoint[j].x, inPoint[j].y)
                }
            }
            path.close()
            canvas?.drawPath(path, mLinePaint)
            path.reset()
        }
        canvas?.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        if (mWidth < mHeight) {
            mWidth = mHeight
        } else {
            mHeight = mWidth
        }

        centerPoint.x = (mWidth / 2).toFloat()
        centerPoint.y = centerPoint.x
        Log.e("xfhy", "w = $w  h=$h")
        mBigRadius = mWidth / 3
        mBar = mBigRadius / mIntervalCount
        initPoints()
    }

    private fun initPoints() {
        // 一个数组中每个元素又一是一个点数组,有几个多边形就有几个数组
        pointsArrayList = ArrayList()
        for (i in 0 until mIntervalCount) {
            val points = ArrayList<PointF>()
            for (j in 0 until abilityCount) {
                //每一个圈的半径
                val r = mBigRadius * ( (4 - i) / mIntervalCount.toFloat() )
                val x = r * Math.cos(j * mAngle - Math.PI / 2)
                val y = r * Math.sin(j * mAngle - Math.PI / 2)
                points.add(PointF(x.toFloat(), y.toFloat()))
            }
            pointsArrayList?.add(points)
        }
        invalidate()
    }

}