package com.xfhy.readpicture;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.xfhy.readpicture.adapter.ImageAdapter;
import com.xfhy.readpicture.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSION_STATE = 1000;
    private RecyclerView mImageList;
    private Context mContext;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initView();
        initPermission();
    }

    private void initView() {
        mImageList = (RecyclerView) findViewById(R.id.rv_image);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mImageList.setLayoutManager(layoutManager);

    }

    private List<Image> getData() {
        mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        List<Image> imageList = new ArrayList<>();
        while (mCursor != null && mCursor.moveToNext()) {
            //获取图片的名称
            String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media
                    .DISPLAY_NAME));
            //获取图片的路径
            byte[] data = mCursor.getBlob(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media
                    .TITLE));
            Image image = new Image(name, new String(data, 0, data.length - 1), desc);
            imageList.add(image);
        }
        return imageList;
    }

    /**
     * 统一申请权限
     */
    private void initPermission() {
        Log.e(TAG, "initPermission: 申请权限");
        //申请权限
        //检查用户是否已经给我们授权了权限,相等则已经授权,不等则没授权
        boolean haveReadSD = ContextCompat.checkSelfPermission(mContext, Manifest.permission
                .READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!haveReadSD) {
            //参数:Context上下文,权限数组,申请码(申请码只要唯一就行)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STATE);
        } else {
            setupView();
            Log.e(TAG, "initPermission: 有权限");
        }

    }

    private void setupView() {
        mImageList.setAdapter(new ImageAdapter(this, getData()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_STATE:
                //数组中有值    数组第一个就是上面所申请的权限的第一个的是否成功的值   如果为0(PERMISSION_GRANTED)则成功
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    //申请权限成功
                    Toast.makeText(mContext, "申请权限成功,谢谢配合~", Toast.LENGTH_SHORT).show();
                    setupView();
                } else {
                    //申请权限失败
                    Toast.makeText(mContext, "开启权限失败,请授予权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

}
