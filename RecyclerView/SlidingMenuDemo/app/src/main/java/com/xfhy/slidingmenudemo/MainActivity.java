package com.xfhy.slidingmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xfhy.slidingmenudemo.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private MyAdapter mAdapter;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mRecyclerview = (RecyclerView) findViewById(R.id.rv_test);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mAdapter = new MyAdapter(getData());
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerview.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerview.addItemDecoration(mDividerItemDecoration);
        mRecyclerview.setAdapter(mAdapter);
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(i + "------");
        }
        return dataList;
    }
}
