package com.xfhy.hoteltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.xfhy.hoteltest.adapter.KeySectionAdapter;
import com.xfhy.hoteltest.adapter.SectionedRecyclerViewAdapter;
import com.xfhy.hoteltest.adapter.SectionedSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private KeySectionAdapter mAdapter;
    private RecyclerView rvKeywords;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initAdapter();
    }

    private void initView() {
        rvKeywords = (RecyclerView) findViewById(R.id.rv_keywords);
    }

    private void initAdapter() {
        mAdapter = new KeySectionAdapter(this);

        //设置一个Child的点击事件
        /*mAdapter.setOnChildClickListener(new SectionedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(int position, int viewType) {
                Toast.makeText(MainActivity.this, "点击头像位置:" + position, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChildClick: position:" + position + "   viewType:" + viewType);
            }
        });

        mAdapter.setOnItemClickListener(new SectionedRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int section, int position) {
                Toast.makeText(MainActivity.this, "点击美女的位置为:" + position, Toast.LENGTH_SHORT)
                .show();
                Log.e(TAG, "onItemClick: section:"+section+"  position:"+position);
            }
        });

        mAdapter.setOnItemLongClickListener(new SectionedRecyclerViewAdapter
        .OnItemLongClickListener() {


            @Override
            public void onItemLongClick(int section, int position) {
                Toast.makeText(MainActivity.this, "长按  美女的位置为:" + position, Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "onItemLongClick: section:"+section+"  position:"+position);
            }
        });
*/
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mGridLayoutManager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,
                mGridLayoutManager));
        rvKeywords.setLayoutManager(mGridLayoutManager);
        rvKeywords.setAdapter(mAdapter);
        //mDivider = new SectionedGridDivider(this, 50, Color.parseColor("#F5F5F5"));
//        rvKeywords.addItemDecoration(mDivider);

        mAdapter.setmData(getData());

    }

    private List<KeyResponse> getData() {
        List<KeyResponse> keyResponseList = new ArrayList<>();

        KeyResponse keyResponse1 = new KeyResponse();
        keyResponse1.setHeaderText("行政区");
        List<String> normalText = new ArrayList<>();
        normalText.add("武侯区");
        normalText.add("讲讲去");
        normalText.add("aa");
        normalText.add("bb");
        normalText.add("cc");
        normalText.add("dd");
        normalText.add("ee");
        normalText.add("qq");
        normalText.add("ee");
        normalText.add("rr");
        normalText.add("uu");
        normalText.add("oo");
        keyResponse1.setNormalList(normalText);
        keyResponseList.add(keyResponse1);

        KeyResponse keyResponse2 = new KeyResponse();
        keyResponse2.setHeaderText("商区");
        List<String> normalText2 = new ArrayList<>();
        normalText2.add("武侯区a");
        normalText2.add("讲讲去a");
        normalText2.add("aaa");
        normalText2.add("bba");
        normalText2.add("cca");
        normalText2.add("dda");
        normalText2.add("eae");
        normalText2.add("qqa");
        normalText2.add("eaea");
        normalText2.add("rar");
        normalText2.add("uua");
        normalText2.add("ooa");
        keyResponse2.setNormalList(normalText2);
        keyResponseList.add(keyResponse2);

        return keyResponseList;
    }

}
