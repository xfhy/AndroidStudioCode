package com.xfhy.mvprxjavaretrofit.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xfhy.mvprxjavaretrofit.service.entity.Joke;
import com.xfhy.mvprxjavaretrofit.service.manager.DataManager;
import com.xfhy.mvprxjavaretrofit.service.view.JokeView;
import com.xfhy.mvprxjavaretrofit.service.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xfhy on 2017/8/12.
 */

public class JokePresenter implements BasePresenter {

    private static final String TAG = "JokePresenter";
    private Context mContext;
    /**
     * 这个类就是为了更方便的调用RetrofitService中定义的方法
     */
    private DataManager dataManager;
    private JokeView mJokeView;
    private Joke mJoke;

    public JokePresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        dataManager = new DataManager(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View view) {
        mJokeView = (JokeView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    /**
     * 用DataManager去请求数据,并用JokeView更新UI
     *
     * @param authorization
     * @param pageNum
     * @param pageSize
     * @param sort
     */
    public void getJoke(String authorization, int
            pageNum, int pageSize, String sort) {
        dataManager.getJoke(authorization, pageNum, pageSize, sort)
                .subscribeOn(Schedulers.io())  //请求事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())  //定义事件在主线程消费
                .subscribe(new Observer<Joke>() { //最后通过subscribe使观察者订阅它

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Joke joke) {
                        Log.e(TAG, "onNext: " + joke.toString());
                        mJoke = joke;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e.getLocalizedMessage());
                        mJokeView.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (mJoke != null) {
                            mJokeView.onSuccess(mJoke);
                        }
                        Log.e(TAG, "onComplete: ");
                        if (!mDisposable.isDisposed()) {
                            mDisposable.dispose();  //取消订阅
                        }
                    }
                });

    }

}
