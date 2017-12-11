package com.xfhy.hoteltest.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

/**
 * package: com.easyandroid.sectionadapter.adapter.SectionedSpanSizeLookup
 * author: gyc
 * description:分组每行显示数量的管理类
 * time: create at 2017/7/7 23:19
 */

public class SectionedSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected SectionedRecyclerViewAdapter<?, ?, ?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;

    public SectionedSpanSizeLookup(SectionedRecyclerViewAdapter<?, ?, ?, ?, ?> adapter,
                                   GridLayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.hasHeader()) {//列表顶部有header
            if (position == 0) {
                return layoutManager.getSpanCount();
            } else if (position < adapter.getItemCount()) {//xxxxxxx
                Log.e("TAG", "30");
                if (adapter.isSectionHeaderPosition(position - 1) || adapter
                        .isSectionFooterPosition(position - 1)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            } else {
                return layoutManager.getSpanCount();
            }
        } else {//列表顶部没有header
            if (position < adapter.getItemCount()) {  //xxxxxxx
                Log.e("TAG", "43   position=" + position);
                if (adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition
                        (position)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            } else {
                return layoutManager.getSpanCount();
            }
        }
    }
}
