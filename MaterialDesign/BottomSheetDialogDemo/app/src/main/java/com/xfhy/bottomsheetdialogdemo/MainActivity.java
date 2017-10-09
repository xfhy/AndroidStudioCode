package com.xfhy.bottomsheetdialogdemo;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.xfhy.bottomsheetdialogdemo.adapter.ShareListAdapter;
import com.xfhy.bottomsheetdialogdemo.entity.AppInfoEntity;
import com.xfhy.bottomsheetdialogdemo.util.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mShareBtn;
    private Intent shareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mShareBtn = (Button) findViewById(R.id.btn_share);
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
    }

    private void showShareDialog() {
        //官方的底部分享Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_share);
        RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id
                .rv_share_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "www.github.com");
        ArrayList<AppInfoEntity> shareAppList = Utils.getShareAppList(this, shareIntent);
        ShareListAdapter shareListAdapter = new ShareListAdapter(shareAppList, this);
        recyclerView.setAdapter(shareListAdapter);

        bottomSheetDialog.show();

    }


}
