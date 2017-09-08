package com.xfhy.mvprxjavaretrofit.service.manager;

import android.content.Context;

import com.xfhy.mvprxjavaretrofit.service.RetrofitHelper;
import com.xfhy.mvprxjavaretrofit.service.RetrofitService;
import com.xfhy.mvprxjavaretrofit.service.entity.Joke;

import io.reactivex.Observable;

/**
 * Created by xfhy on 2017/8/12.
 * 这个类就是为了更方便的调用RetrofitService中定义的方法
 * 以后要调用RetrofitService中的方法直接在这里面调用就行了,不需要重复建立RetrofitService的实例
 */

public class DataManager {

    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    /**
     * 为了更方便的获取joke
     *
     * @param authorization Authorization
     *                      请求Header中添加的Authorization字段；配置Authorization字段的值为“APPCODE ＋ 半角空格
     *                      ＋APPCODE值”。
     * @param pageNum       STRING	必选	页码
     * @param pageSize      STRING	必选	每页条数 最大20
     * @param sort          STRING	必选	排序 addtime按时间倒叙 rand随机获取 sort=rand时，pagenum无效
     */
    public Observable<Joke> getJoke(String authorization, int
            pageNum, int pageSize, String sort) {
        return mRetrofitService.getJoke(authorization, pageNum, pageSize, sort);
    }

}
