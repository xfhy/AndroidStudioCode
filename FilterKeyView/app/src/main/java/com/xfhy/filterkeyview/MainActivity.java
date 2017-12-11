package com.xfhy.filterkeyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.filterkeyview.widget.FilterKeyView;
import com.xfhy.filterkeyview.widget.model.FilterKeyModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FilterKeyView filterKeyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterKeyView = (FilterKeyView) findViewById(R.id.fkv_test);

        filterKeyView.initKeyList(getData());

        filterKeyView.setOnStateChangeListener(new FilterKeyView.OnChangeStateChangeListener() {
            @Override
            public void onChange(List<FilterKeyModel> keyList) {
                Log.d(TAG, "onChange: " + keyList.toString());

            }

            @Override
            public void onExpand(int position) {
                Log.d(TAG, "onExpand: position=" + position);
                //这时展开PopupWindow   HotelScoreView评分选择控件返回的数据在外部处理
            }

            @Override
            public void onPutAway(int position) {
                Log.d(TAG, "onPutAway: position=" + position);
                //这时收起PopupWindow
            }
        });
    }

    private List<FilterKeyModel> getData() {
        List<FilterKeyModel> filterKeyModels = new ArrayList<>();

        FilterKeyModel filterKeyModel = new FilterKeyModel();
        filterKeyModel.keyName = "含早";
        filterKeyModels.add(filterKeyModel);
        FilterKeyModel filterKeyModel2 = new FilterKeyModel();
        filterKeyModel2.keyName = "WIFI";
        filterKeyModels.add(filterKeyModel2);
        FilterKeyModel filterKeyModel3 = new FilterKeyModel();
        filterKeyModel3.keyName = "立即确认";
        filterKeyModels.add(filterKeyModel3);
        FilterKeyModel filterKeyModel4 = new FilterKeyModel();
        filterKeyModel4.keyName = "免费取消";
        filterKeyModels.add(filterKeyModel4);
        FilterKeyModel filterKeyModel5 = new FilterKeyModel();
        filterKeyModel5.keyName = "评分";
        filterKeyModel5.canExpand = true;
        filterKeyModels.add(filterKeyModel5);
        FilterKeyModel filterKeyModel6 = new FilterKeyModel();
        filterKeyModel6.keyName = "随便加的";
        filterKeyModels.add(filterKeyModel6);

        return filterKeyModels;
    }
}
