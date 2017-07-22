package com.xfhy.day05task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xfhy.day05task.R;
import com.xfhy.day05task.adapter.SwitchMoviePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/12.
 * 主界面的Fragment
 */

public class MainContentFragment extends Fragment implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    private static final String TAG = "MainContentFragment";
    private ViewPager mViewPager;
    private View mIndicator;
    private TextView mLatest;
    private TextView mHottest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main_content);
        mIndicator = view.findViewById(R.id.view_indicator);
        mLatest = (TextView) view.findViewById(R.id.tv_latest);
        mHottest = (TextView) view.findViewById(R.id.tv_hottest);

        SwitchMoviePagerAdapter pagerAdapter = new SwitchMoviePagerAdapter(getActivity()
                .getSupportFragmentManager(), getData());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mLatest.setOnClickListener(this);
        mHottest.setOnClickListener(this);
    }

    private List<Fragment> getData() {
        LateMovieFragment latestFragment = new LateMovieFragment();
        HotMovieFragment hottestFragment = new HotMovieFragment();
        List<Fragment> dataList = new ArrayList<>();
        dataList.add(latestFragment);
        dataList.add(hottestFragment);
        return dataList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG, "onPageScrolled: positionOffset--" + positionOffset);
        FrameLayout.LayoutParams latestLayoutParams = (FrameLayout.LayoutParams) mLatest
                .getLayoutParams();
        FrameLayout.LayoutParams hotLayoutParams = (FrameLayout.LayoutParams) mHottest
                .getLayoutParams();

        //通过属性动画将mIndicator移动过去
        int width = hotLayoutParams.leftMargin - latestLayoutParams.leftMargin;
        mIndicator.setTranslationX(width * positionOffset + position * width);
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected: ");

        switch (position) {
            case 0:
                mLatest.setTextColor(getResources().getColor(R.color.colorwhite));
                mHottest.setTextColor(getResources().getColor(R.color.colorgray));
                break;
            case 1:
                mLatest.setTextColor(getResources().getColor(R.color.colorgray));
                mHottest.setTextColor(getResources().getColor(R.color.colorwhite));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e(TAG, "onPageScrollStateChanged: ");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_latest:
                //将指定位置的Item滚动到屏幕中央
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_hottest:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 先写来放起,以后用得到.Fragment一般是通过这个方法来实例化的,而且也方便传值
     * @param data 需要传递过来的值  可以传递对象
     * @return
     */
    public static MainContentFragment newInstance(String data) {

        Bundle args = new Bundle();

        MainContentFragment fragment = new MainContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
