package com.xfhy.loadbigimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xfhy.loadbigimage.adapter.ImageAdapter;
import com.xfhy.loadbigimage.widget.LoadingProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private LoadingProgressDialog loadingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setupView();
    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_photo_test);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new ImageAdapter(R.layout.item_image);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mAdapter);


        loadingProgressDialog = new LoadingProgressDialog(this, "正在加载中...", R.drawable
                .animation_loading);
    }

    private void setupView() {
        getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> s) throws Exception {
                        mAdapter.addData(s);
                        Log.e("xfhy", s.toString());
                        loadingProgressDialog.dismiss();
                    }
                });
    }

    private Observable<List<String>> getData() {
        loadingProgressDialog.show();
        Observable<List<String>> listObservable = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> e) throws Exception {
                for (int i = 2; i <= 2; i++) {
                    Document document = Jsoup.connect("http://www.zjito" +
                            ".com/tpfl/xg/index_" + i +
                            ".shtml").get();

                    //需要判断末尾是shtml
                    Elements els = document.select("a[href$=shtml]");
                    //Log.e(TAG, "1, HTML内容:" + els.toString());
                    List<String> imageUrls = new ArrayList<>();
                    for (int j = 0; j < els.size(); j++) {
                        Element el = els.get(j);
                        //Log.e("1,标题",el.text());

                        //这里进入第二页
                        String href = el.attr("href");
                        Log.e("2,链接", href);

                        if (href.contains("index")) {
                            continue;
                        }

                        //爬里面的详情页
                        Document doc_detail = Jsoup.connect(href).get();
                        //这个是图片列表  这个图片列表就是,该详情页美女的所有图片
                        /**
                         * 内容如下
                         * <li class="">
                         <div class="div-img">
                         <div class="div-num" data-src="http://www.zjito
                         .com/upload/resources/image/2016/8/13/b33c6c79-b89e-44b2-baf1
                         -469c4676c5a1_720x1500.jpg?1483474821000">1</div>
                         </div>
                         </li>
                         <li class="">
                         <div class="div-img">
                         <div class="div-num" data-src="http://www.zjito
                         .com/upload/resources/image/2016/8/13/5181232e-f9a0-488d-b11d
                         -fb4cfb63e26e_720x1500.jpg?1483474821000">2</div>
                         </div>
                         </li>
                         <li class="">
                         <div class="div-img">
                         <div class="div-num" data-src="http://www.zjito
                         .com/upload/resources/image/2016/8/13/8e8a3275-44c2-477a-98ee
                         -723a2290ed68_720x1500.jpg?1483474716000">3</div>
                         </div>
                         </li>
                         */
                        Elements girlPictureList = doc_detail.select("div.div-num");

                        //遍历这个列表即可
                        for (int k = 0; k < girlPictureList.size(); k++) {
                            Element element = girlPictureList.get(k);
                            String src = element.attr("data-src");
                            Log.e("3,图片", src);
                            imageUrls.add(src);
                        }

                    }
                    e.onNext(imageUrls);
                }
            }
        });
        return listObservable;
    }

}
