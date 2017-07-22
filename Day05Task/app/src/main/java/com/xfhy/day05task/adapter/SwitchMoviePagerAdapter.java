package com.xfhy.day05task.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class SwitchMoviePagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SwitchMoviePagerAdapter";
    private List<Fragment> dataList;

    public SwitchMoviePagerAdapter(FragmentManager fm, List<Fragment> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {

        if (dataList != null && dataList.size() > position) {
            Fragment fragment = dataList.get(position);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    /**
     * 更新数据
     *
     * @param data
     */
    private void updateRes(List<String> data) {

    }

}
