package com.xfhy.mvprxjavaretrofit.service;

import android.content.Context;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xfhy on 2017/8/12.
 * Retrofit的帮助类  用于Retrofit的初始化
 */

public class RetrofitHelper {

    public static final String JOKE_BASE_URL = "http://jisuxhdq.market.alicloudapi.com";

    private Context mContext;
    private OkHttpClient mClient = new OkHttpClient();
    private GsonConverterFactory mFactory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    /**
     * 私有化构造方法
     *
     * @param context
     */
    private RetrofitHelper(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * 获取RetrofitHelper的实例
     *
     * @param context
     * @return
     */
    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(JOKE_BASE_URL)  //主机地址
                .client(mClient)  //用于请求HTTP客户端
                .addConverterFactory(mFactory)  //添加转换器工厂用于序列化和反序列化对象。
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //添加一个Call适配器工厂，用于支持Call以外的服务方法返回类型。 这里是添加RxJava
                .build();
    }

    /**
     * 创建由服务接口定义的API端点的实现。
     * @return
     */
    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }

}
