package com.xfhy.timeline.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.xfhy.timeline.R;
import com.xfhy.timeline.util.ResourceUtil;

/**
 * author feiyang
 * create at 2017/9/7 19:02
 * description：自定义时间轴view
 
 
 - 获取自定义属性的值
 - 通过setBounds控制Drawable的边界
 - 一个View中含有多个Drawable的话,则需要控制其放置的位置  边界
 
 */
public class TimeLineMarkerView extends View {

    /**
     * 圆点大小,单位为dp
     */
    private int mMarkerSize = 24;
    /**
     * 线段粗细
     */
    private int mLineSize = 2;
    /**
     * 上面线段颜色或者图片
     */
    private Drawable mBeginLine;
    /**
     * 下面线段颜色或者图片
     */
    private Drawable mEndLine;
    /**
     * 圆点颜色或图片
     */
    private Drawable mMarkerDrawable;
    /**
     * 竖向还是横向   false:竖向   true:横向
     */
    private boolean orientation = false;

    public TimeLineMarkerView(Context context) {
        this(context, null);
    }

    public TimeLineMarkerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineMarkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //获取自定义属性的值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable
                .TimeLineMarkerView);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarkerView_markerSize,
                mMarkerSize);

        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarkerView_lineSize,
                mLineSize);

        mBeginLine = typedArray.getDrawable(R.styleable.TimeLineMarkerView_beginLine);

        mEndLine = typedArray.getDrawable(R.styleable.TimeLineMarkerView_endLine);

        mMarkerDrawable = typedArray.getDrawable(R.styleable.TimeLineMarkerView_marker);

        orientation = typedArray.getBoolean(R.styleable.TimeLineMarkerView_orientation,
                orientation);
        //最后记得回收
        typedArray.recycle();

        //以下  不解
        if (mBeginLine != null) {
            mBeginLine.setCallback(this);
        }

        if (mEndLine != null) {
            mEndLine.setCallback(this);
        }

        if (mMarkerDrawable != null) {
            mMarkerDrawable.setCallback(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        //判断横向还是竖向   true为横向
        if (orientation) {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                //针对wrap情况做处理
                setMeasuredDimension(120, 80);
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, heightSpecSize);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSpecSize, 80);
            }
        } else {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                //针对wrap情况做处理
                setMeasuredDimension(80, 120);
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(80, heightSpecSize);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSpecSize, 120);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		
		//绘制边界
        if (mBeginLine != null) {
            mBeginLine.draw(canvas);
        }
        if (mEndLine != null) {
            mEndLine.draw(canvas);
        }
        if (mMarkerDrawable != null) {
            mMarkerDrawable.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();
    }

    private void initDrawableSize() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();
        //view宽度
        int width = getWidth();
        int height = getHeight();
        //圆点宽度
        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;
        Rect bounds;
        //圆点大小
        int mMarkerSizepx = ResourceUtil.dip2px(getContext(), mMarkerSize);
        //线条的粗度
        int mLineSizepx = ResourceUtil.dip2px(getContext(), mLineSize);

        if (orientation) {
            //横向
            if (mMarkerDrawable != null) {
                //圆点大小
                int markSize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                //确定圆点位置
                mMarkerDrawable.setBounds(pLeft + width / 2 - markSize / 2, pTop, pLeft + width /
                                2 + markSize / 2,
                        pTop + markSize);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + width / 2, pTop, pLeft + width / 2, pTop);
            }
            //线条的高度的一半
            int halfLine = mLineSizepx >> 1;
            //线条也是有粗细的
            int lineTop = bounds.centerY() - halfLine;
            if (mBeginLine != null) {
                mBeginLine.setBounds(0, lineTop, bounds.left, lineTop + mLineSizepx);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(bounds.right, lineTop, width, lineTop + mLineSizepx);
            }
        } else {
            //竖向
            //确定圆点位置
            if (mMarkerDrawable != null) {
                int markSize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                mMarkerDrawable.setBounds(pLeft, pTop + height / 2 - markSize / 2, pLeft + markSize,
                        pTop + height / 2 + markSize / 2);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + mLineSizepx / 2, pTop + height / 2, pLeft + mLineSizepx
                        / 2, pTop + height / 2);
            }
            //线条的粗度的一半
            int halfLine = mLineSizepx >> 1;
            //线条左边
            int lineLeft = bounds.centerX() - halfLine;
            if (mBeginLine != null) {
                mBeginLine.setBounds(lineLeft, 0, lineLeft + mLineSizepx, bounds.top);
            }
            if (mEndLine != null) {
                mEndLine.setBounds(lineLeft, bounds.bottom, lineLeft + mLineSizepx, height);
            }
        }

    }

    /**
     * 设置线条粗细
     *
     * @param lineSize 线条粗度
     */
    public void setLineSize(int lineSize) {
        if (this.mLineSize != lineSize) {
            mLineSize = lineSize;
            initDrawableSize();
            invalidate();
        }
    }

    /**
     * 设置圆点大小
     *
     * @param markerSize 圆点大小
     */
    public void setMarkerSize(int markerSize) {
        if (this.mMarkerSize != markerSize) {
            mMarkerSize = markerSize;
            initDrawableSize();
            invalidate();
        }
    }

    /**
     * 设置左(上)线条的图片资源
     *
     * @param beginLine 左(上)线条
     */
    public void setBeginLine(Drawable beginLine) {
        if (this.mBeginLine != beginLine) {
            this.mBeginLine = beginLine;
            if (mBeginLine != null) {
                mBeginLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    /**
     * 设置右(下)线条的图片资源
     *
     * @param endLine 右(下)线条
     */
    public void setEndLine(Drawable endLine) {
        if (this.mEndLine != endLine) {
            this.mEndLine = endLine;
            if (mEndLine != null) {
                mEndLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    /**
     * 设置圆点资源
     * @param markerDrawable 圆点资源
     */
    public void setMarkerDrawable(Drawable markerDrawable) {
        if (this.mMarkerDrawable != markerDrawable) {
            this.mMarkerDrawable = markerDrawable;
            if (mMarkerDrawable != null) {
                mMarkerDrawable.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

}
