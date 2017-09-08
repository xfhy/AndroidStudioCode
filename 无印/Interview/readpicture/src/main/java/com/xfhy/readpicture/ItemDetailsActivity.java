package com.xfhy.readpicture;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xfhy.readpicture.entity.Image;

import java.util.List;

/**
* 图片详情
* @author xfhy
* create at 2017年6月29日21:56:47
*/
public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
    }


    public static void actionStart(Context context, List<Image> imageList,int current){

    }

}
