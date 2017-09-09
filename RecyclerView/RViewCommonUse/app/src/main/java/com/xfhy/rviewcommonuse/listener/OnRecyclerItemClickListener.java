package com.xfhy.rviewcommonuse.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xfhy on 2017/9/9 17:56.
 * Description
 * <p>
 * 下面的代码很简单没什么复杂的地方，就是通过一个手势探测器 GestureDetectorCompat 来探测屏幕事件，然后通过手势监听器 SimpleOnGestureListener
 * 来识别手势事件的种类，然后调用我们设置的对应的回调方法。这里值得说的是：当获取到了 RecyclerView 的点击事件和触摸事件数据
 * MotionEvent，那么如何才能知道点击的是哪一个 item 呢？
 * <p>
 * RecyclerView已经为我们提供了这样的方法：findChildViewUnder()。
 * <p>
 * 我们可以通过这个方法获得点击的 item ，同时我们调用 RecyclerView 的另一个方法 getChildViewHolder()，可以获得该 item 的
 * ViewHolder，最后再回调我们定义的虚方法 onItemClick() 就ok了，这样我们就可以在外部实现该方法来获得 item 的点击事件了。
 * <p>
 * 作者：OCNYang
 * 链接：http://www.jianshu.com/p/70788a7a5547
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    /**
     * 手势探测器
     */
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new
                ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        /**
         * 一次单独的轻触抬起手操作,就是普通的点击事件
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder
                        (childViewUnder);
                onItemClick(childViewHolder);
            }
            return true;
        }

        /**
         * 长按屏幕超过一定时长,就是长按事件
         */
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder
                        (childViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }


}
