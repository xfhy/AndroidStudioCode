package com.xfhy.day05task.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/12.
 * 最新电影  的Fragment
 */

public class LateMovieFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView view = new TextView(getActivity());
        view.setBackgroundColor(Color.RED);
        view.setGravity(Gravity.CENTER);
        return view;
    }



}
