package com.xfhy.roundimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author feiyang
 *         time create at 2017/11/21 16:42
 *         description
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 圆角大小，默认为0
     */
    private int mBorderRadius = 0;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix = new Matrix();
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderRadius = (int) typedArray.getDimension(R.styleable
                .RoundImageView_riv_radius, 0);
        typedArray.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //既然要自己写,那么就不调用父类的onDraw()了
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = drawableToBitmap(drawable);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        if (bitmap.getWidth() != getWidth() || bitmap.getHeight() != getHeight()) {
            scaleX = getWidth() * 1.0f / bitmap.getWidth();
            scaleY = getHeight() * 1.0f / bitmap.getHeight();
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scaleX, scaleY);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mBorderRadius, mBorderRadius,
                mPaint);
    }

    /**
     * Drawable转Bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所以当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
