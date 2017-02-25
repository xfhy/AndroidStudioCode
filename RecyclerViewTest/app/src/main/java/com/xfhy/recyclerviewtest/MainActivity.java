package com.xfhy.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xfhy.recyclerviewtest.adapter.FruitAdapter;
import com.xfhy.recyclerviewtest.model.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();  //1. 初始化水果数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_fruit);

        //2. 创建LayoutManager对象
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  //设置水平排列

        //创建StaggeredGridLayoutManager实例,   参数:列数,排列方向
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);

        //3. 设置LayoutManager对象  LayoutManager用于指定RecyclerView的布局方式,这里使用的LinearLayoutManager
        //是线性布局的意思    可以实现和ListView类似的效果
        recyclerView.setLayoutManager(layoutManager);

        //设置网格布局
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //4. 创建Adapter对象
        FruitAdapter fruitAdapter = new FruitAdapter(fruitList);
        //5. 设置Adapter
        recyclerView.setAdapter(fruitAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initFruits() {
        for (int i = 0; i < 10; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }

}
