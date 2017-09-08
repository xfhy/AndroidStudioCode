package com.xfhy.rxjavanetworktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 2017年7月30日21:40:29
 * 简单来讲:
 * 使用RxJava，你可以使用subscribeOn()指定被观察者代码运行的线程，使用observerOn()指定订阅者运行的线程
 */
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SecondActivity";
    public static final String BAIDU_URL = "http://www.baidu.com/";
    private Button btRequest;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();

    }

    private void initView() {
        btRequest = (Button) findViewById(R.id.bt_request);
        tvContent = (TextView) findViewById(R.id.tv_html_content);

        btRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_request:
                requestNetwork();
                break;
        }
    }

    /**
     * 访问网络
     */
    private void requestNetwork() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                //这是在IO线程
                Log.e(TAG, "subscribe: okHttp请求  线程"+Thread.currentThread().getName());
                Request.Builder builder = new Request.Builder().url(BAIDU_URL).get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).subscribeOn(Schedulers.io())   //被订阅者运行的线程   发射器
        .observeOn(AndroidSchedulers.mainThread())    //订阅者运行的线程
        .subscribe(new Consumer<Response>() {
            @Override
            public void accept(Response response) throws Exception {
                //这是在主线程
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String result = response.body().string();
                        Log.e(TAG, "accept: 请求成功线程" + Thread.currentThread().getName());
                        tvContent.setText(result);
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "accept: 请求失败线程"+Thread.currentThread().getName());
                tvContent.setText("请求失败");
            }
        });
    }
}
