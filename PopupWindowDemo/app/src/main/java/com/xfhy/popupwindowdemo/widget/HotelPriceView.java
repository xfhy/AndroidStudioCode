package com.xfhy.popupwindowdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xfhy.popupwindowdemo.R;

/**
 * @author xfhy
 *         create at 2017/12/2 10:04
 *         description：
 */
public class HotelPriceView extends View {


    private static final int DEFAULT_FONT_SIZE = 15;
    private static final int DEFAULT_POINT_SIZE = 3;
    private static final int DEFAULT_SELECTED_COLOR = 0xff9133;
    private static final int DEFAULT_UNSELECTED_COLOR = 0xcdcdcd;
    private int mFontSize = DEFAULT_FONT_SIZE;
    private int mPointSize = DEFAULT_POINT_SIZE;
    private int mSelectColor = DEFAULT_SELECTED_COLOR;
    private int mUnSelectColor = DEFAULT_UNSELECTED_COLOR;
    private String mPrice = "";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mHeight = 0;
    private int mWidth = 0;
    private int mTextWidth;
    private int mTextHeight;
    private boolean mSelected = true;


    public HotelPriceView(Context context) {
        this(context, null);
    }

    public HotelPriceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotelPriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HotelPriceView);
        mFontSize = (int) typedArray.getDimension(R.styleable.HotelPriceView_fontSize,
                DEFAULT_FONT_SIZE);
        mSelectColor = typedArray.getColor(R.styleable.HotelPriceView_selectColor,
                DEFAULT_SELECTED_COLOR);
        mUnSelectColor = typedArray.getColor(R.styleable.HotelPriceView_unSelectColor,
                DEFAULT_UNSELECTED_COLOR);
        mPrice = typedArray.getString(R.styleable.HotelPriceView_price);
        mPointSize = (int) typedArray.getDimension(R.styleable.HotelPriceView_pointSize,
                DEFAULT_POINT_SIZE);
        typedArray.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mSelected ? mSelectColor : mUnSelectColor);
        drawPriceText(canvas);
        drawPoint(canvas);
    }

    private void drawPoint(Canvas canvas) {
        mPaint.setStrokeWidth(mPointSize);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(mWidth / 2, mHeight / 2 + mTextHeight / 2, mPaint);
    }

    private void drawPriceText(Canvas canvas) {
        mPaint.setTextSize(mFontSize);
        Rect rect = new Rect();
        mPaint.getTextBounds(mPrice, 0, mPrice.length(), rect);
        //文字宽
        mTextWidth = rect.width();
        //文字高
        mTextHeight = rect.height();
        canvas.drawText(mPrice, mWidth / 2 - mTextWidth / 2, mTextHeight, mPaint);
    }

    @Override
    public boolean isSelected() {
        return mSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        mSelected = selected;
        invalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mSelected = enabled;
        invalidate();
    }
}
