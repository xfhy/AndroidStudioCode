package com.xfhy.coordinatorlayout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xfhy.coordinatorlayout.R;
import com.xfhy.coordinatorlayout.adapter.TabFragmentAdapter;
import com.xfhy.coordinatorlayout.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/11.
 * 这是ViewPager中的一页,Fragment
 */

public class TabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TabFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tab);

        mAdapter = new TabFragmentAdapter(getData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private List<ItemBean> getData() {
        List<ItemBean> itemBeanList = new ArrayList<>();
        for (int i=0; i<50; i++){
            itemBeanList.add(new ItemBean("Item "+i));
        }
        return itemBeanList;
    }


}
