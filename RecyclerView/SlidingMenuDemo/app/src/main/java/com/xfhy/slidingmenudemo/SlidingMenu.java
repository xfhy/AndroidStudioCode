package com.xfhy.slidingmenudemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.xfhy.slidingmenudemo.util.DeviceUtil;

/**
 * author feiyang
 * create at 2017/8/30 8:56
 * description： 自定义侧滑菜单   RecyclerView简单实现侧滑
 */
public class SlidingMenu extends HorizontalScrollView {

    /**
     * 侧滑菜单所占屏幕比例
     */
    private static final float RADIO = 0.5f;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    /**
     * 第一次进入
     */
    private boolean once = true;
    /**
     * 是否打开了菜单
     */
    private boolean isOpen;
    /**
     * 监听器
     */
    private MenuStateListener mListener;
    /**
     * 是否允许滑动
     */
    private boolean isAllowScroll;

    public SlidingMenu(Context context) {
        super(context);

        initView(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        mScreenWidth = DeviceUtil.getDeviceSize(context).widthPixels;
        mMenuWidth = (int) (mScreenWidth * RADIO);
        //设置滚动模式  不允许用户过度滑动
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        //设置不绘制滚动条
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            //必须外层是LinearLayout    并且LinearLayout里面还有2个LinearLayout
            //才这样写
            LinearLayout wrapper = (LinearLayout) getChildAt(0);

            //设置正常显示的item布局的宽度
            View childAtOne = wrapper.getChildAt(0);
            ViewGroup.LayoutParams layoutParams = childAtOne.getLayoutParams();
            layoutParams.width = mScreenWidth;
            childAtOne.setLayoutParams(layoutParams);

            //设置item的侧滑菜单的宽度
            View childAtTwo = wrapper.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsTwo = childAtTwo.getLayoutParams();
            layoutParamsTwo.width = mMenuWidth;
            childAtTwo.setLayoutParams(layoutParamsTwo);

            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //关闭其他item菜单项
                mListener.closeOtherSlideMenu(this);
                break;
            case MotionEvent.ACTION_UP:  //当抬起手指的时候
                int scrollX = getScrollX();
                if (Math.abs(scrollX) > mMenuWidth / 2) {
                    //平滑滚动
                    this.smoothScrollTo(mMenuWidth, 0);
                    //当前菜单打开了
                    mListener.openSlideMenu(this);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    /**
     * 菜单是否已经打开
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 设置菜单滑动的监听器
     *
     * @param listener
     */
    public void setOnMenuStateListener(MenuStateListener listener) {
        this.mListener = listener;
    }

    /**
     * 监听菜单滑动的状态
     */
    public interface MenuStateListener {
        /**
         * 打开了当前这个菜单
         *
         * @param slidingMenu 当前item菜单
         */
        void openSlideMenu(SlidingMenu slidingMenu);

        /**
         * 关闭除了当前的这个item的其他item的菜单
         *
         * @param slidingMenu 当前item菜单
         */
        void closeOtherSlideMenu(SlidingMenu slidingMenu);
    }

}
