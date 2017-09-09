package com.xfhy.rviewcommonuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xfhy.rviewcommonuse.adapter.TextAdapter;
import com.xfhy.rviewcommonuse.listener.OnRecyclerItemClickListener;
import com.xfhy.rviewcommonuse.listener.RecyItemTouchHelperCallback;
import com.xfhy.rviewcommonuse.recyembellish.DividerGridItemDecoration;
import com.xfhy.rviewcommonuse.recyembellish.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_test);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);

        TextAdapter textAdapter = new TextAdapter(getData());
        mRecyclerView.setAdapter(textAdapter);

        //添加分割线
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, LinearLayoutManager
                .VERTICAL));
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        //设置item的触摸事件
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                TextAdapter.ViewHolder viewHolderTemp = (TextAdapter.ViewHolder) viewHolder;
                int childAdapterPosition = mRecyclerView.getChildAdapterPosition(viewHolderTemp
                        .itemView);
                String tvString = viewHolderTemp.tvTest.getText().toString();
                Toast.makeText(MainActivity.this, "逗逗~" + tvString + "  位置:" +
                        childAdapterPosition, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                TextAdapter.ViewHolder viewHolderTemp = (TextAdapter.ViewHolder) viewHolder;
                int childAdapterPosition = mRecyclerView.getChildAdapterPosition(viewHolderTemp
                        .itemView);
                String tvString = viewHolderTemp.tvTest.getText().toString();
                Toast.makeText(MainActivity.this, "长按~" + tvString + "  位置:" +
                        childAdapterPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //实现拖拽操作
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyItemTouchHelperCallback
                (textAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            dataList.add("我是内容" + random.nextInt(1000));
        }
        return dataList;
    }
}
