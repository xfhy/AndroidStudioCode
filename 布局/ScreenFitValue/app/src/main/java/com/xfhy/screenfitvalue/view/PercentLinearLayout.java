package com.xfhy.screenfitvalue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.percent.PercentLayoutHelper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by xfhy on 2017/7/9.
 * 自定义的百分比布局    LinearLayout
 *
 * 就是利用一个PercentLayoutHelper帮助类实现的,这个帮助类的使用非常简单,注释里面很详细.
 */

public class PercentLinearLayout extends LinearLayout {

    /**
     * 帮助类   帮助你实现百分比布局
     *
     * <p>This class collects utility methods that are involved in extracting percentage based dimension
     * attributes and applying them to ViewGroup's children. If you would like to implement a layout
     * that supports percentage based dimensions, you need to take several steps:
     *
     * 步骤如下
     *
     * <ol>
     * <li> You need a {@link ViewGroup.LayoutParams} subclass in your ViewGroup that implements
     * {@link android.support.percent.PercentLayoutHelper.PercentLayoutParams}.
     * <li> In your {@code LayoutParams(Context c, AttributeSet attrs)} constructor create an instance
     * of {@link PercentLayoutHelper.PercentLayoutInfo} by calling
     * {@link PercentLayoutHelper#getPercentLayoutInfo(Context, AttributeSet)}. Return this
     * object from {@code public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo()}
     * method that you implemented for {@link android.support.percent.PercentLayoutHelper.PercentLayoutParams} interface.
     * <li> Override
     * {@link ViewGroup.LayoutParams#setBaseAttributes(TypedArray, int, int)}
     * with a single line implementation {@code PercentLayoutHelper.fetchWidthAndHeight(this, a,
     * widthAttr, heightAttr);}
     * <li> In your ViewGroup override {@link ViewGroup#generateLayoutParams(AttributeSet)} to return
     * your LayoutParams.
     * <li> In your {@link ViewGroup#onMeasure(int, int)} override, you need to implement following
     * pattern:
     * <pre class="prettyprint">
     * protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
     *     mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
     *     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
     *     if (mHelper.handleMeasuredStateTooSmall()) {
     *         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
     *     }
     * }
     * </pre>
     * <li>In your {@link ViewGroup#onLayout(boolean, int, int, int, int)} override, you need to
     * implement following pattern:
     * <pre class="prettyprint">
     * protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
     *     super.onLayout(changed, left, top, right, bottom);
     *     mHelper.restoreOriginalParams();
     * }
     * </pre>
     * </ol>
     */
    private PercentLayoutHelper mPercentLayoutHelper;

    public PercentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPercentLayoutHelper = new PercentLayoutHelper(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPercentLayoutHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPercentLayoutHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPercentLayoutHelper.restoreOriginalParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends LinearLayout.LayoutParams
            implements PercentLayoutHelper.PercentLayoutParams {
        private PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(c, attrs);
        }

        @Override
        public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
            return mPercentLayoutInfo;
        }

        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            PercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

    }

}

