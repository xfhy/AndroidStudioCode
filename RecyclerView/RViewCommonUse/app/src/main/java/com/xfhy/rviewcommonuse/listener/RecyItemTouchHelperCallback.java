package com.xfhy.rviewcommonuse.listener;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xfhy.rviewcommonuse.adapter.TextAdapter;

import java.util.Collections;

/**
 * Created by xfhy on 2017/9/9 20:01.
 * Description
 */

public class RecyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private RecyclerView.Adapter mAdapter;
    /**
     * 是否开启滑动删除
     */
    private boolean isSwipeEnable;
    /**
     * 开启开启长按拖动
     */
    private boolean isFirstDragUnable;

    public RecyItemTouchHelperCallback(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        isSwipeEnable = true;
        isFirstDragUnable = false;
    }

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter, boolean isSwipeEnable,
                                       boolean isFirstDragUnable) {
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
        this.isFirstDragUnable = isFirstDragUnable;
    }

    /**
     * 通过返回值来设置是否处理某次拖拽或者滑动事件
     * 以及拖拽和滑动操作的方向，有以下两种情况：
     * 如果是列表类型的 RecyclerView，拖拽只有 UP、DOWN 两个方向
     * 如果是网格类型的则有 UP、DOWN、LEFT、RIGHT 四个方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
            viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            //拖拽标志
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                    ItemTouchHelper.RIGHT;
            //滑动标志
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //注意：和拖曳的区别就是在这里   可滑动删除
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    /**
     * 当长按并进入拖曳状态时，拖曳的过程中不断的回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        //第一项 则不移动
        if (isFirstDragUnable && toPosition == 0) {
            return false;
        }

        //从上往下
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((TextAdapter) mAdapter).getDataList(), i, i + 1);
            }
        } else {
            //从下往上
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(((TextAdapter) mAdapter).getDataList(), i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 滑动删除的回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //滑动删除
        int adapterPosition = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((TextAdapter) mAdapter).getDataList().remove(adapterPosition);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return !isFirstDragUnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }

    //当长按 item 刚开始拖曳的时候调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //给被拖曳的 item 设置一个深颜色背景
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当完成拖曳手指松开的时候调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //给已经完成拖曳的 item 恢复开始的背景。
        //这里我们设置的颜色尽量和你 item 在 xml 中设置的颜色保持一致
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

}
