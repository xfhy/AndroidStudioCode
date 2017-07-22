package com.xfhy.readpicturedemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取已有图片demo
 *
 * @author xfhy
 *         create at
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSION_STATE = 1000;
    ListView show_list;
    List<String> names = null;
    List<String> descs = null;
    List<String> fileNames = null;
    private Cursor mCursor;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initView();
        initPermission();
        //setupView();

    }

    private void setupView() {
        mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (mCursor != null && mCursor.moveToNext()) {
            //获取图片的名称
            String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media
                    .DISPLAY_NAME));
            //获取图片的路径
            byte[] data = mCursor.getBlob(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media
                    .TITLE));
            names.add(name);
            descs.add(desc);
            fileNames.add(new String(data, 0, data.length - 1));
        }
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names.get(i));
            map.put("desc", descs.get(i));
            listItems.add(map);
        }
        //设置adapter
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, listItems,
                R.layout.item_image, new String[]{"name", "desc"}, new int[]{R.id.tv_name, R.id
                .tv_desc});

        show_list.setAdapter(adapter);


        //list的点击事件
        show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View viewDiag = getLayoutInflater().inflate(R.layout.layout_imageview, null);
                ImageView image = (ImageView) viewDiag.findViewById(R.id.iv_image);
                image.setImageBitmap(BitmapFactory.decodeFile(fileNames.get(i)));
                new AlertDialog.Builder(MainActivity.this).setView(viewDiag)
                        .setPositiveButton("确定", null).show();
            }
        });
    }

    private void initView() {
        show_list = (ListView) findViewById(R.id.lv_show_image_list);

        names = new ArrayList<>();
        descs = new ArrayList<>();
        fileNames = new ArrayList<>();
    }

    /**
     * 统一申请权限
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT < 16) {
            setupView();
        } else {
            Log.e(TAG, "initPermission: 申请权限" );
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
            }
        }

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

    @Override
    protected void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
        super.onDestroy();
    }
}
