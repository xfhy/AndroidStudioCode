package com.xfhy.coordinatorlayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.xfhy.coordinatorlayout.fragment.TabFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xfhy on 2017/6/11.
 * 这是ViewPager的Adapter
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "TabPagerAdapter";
    private List<String> titleList;

    public TabPagerAdapter(FragmentManager fm, List<String> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment tabFragment = new TabFragment();
        return tabFragment;
    }

    @Override
    public int getCount() {
        return titleList != null ? titleList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null && titleList.size() > position) {
            Log.e(TAG, "getPageTitle: "+titleList.get(position));
            return titleList.get(position);
        }
        return "";
    }
}
