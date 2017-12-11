package com.xfhy.fourcompnentdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xfhy.fourcompnentdemo.db.BookDao;
import com.xfhy.fourcompnentdemo.util.ToastUtil;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author feiyang
 *         time create at 2017/11/28 13:37
 *         description
 */
public class BookService extends Service {
    private static final String TAG = "BookService";
    private BookDao mBookDao;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "BookService  onStartCommand()");
        ToastUtil.showMessage("BookService  onStartCommand()");
        mBookDao = BookDao.getInstance();
        addBook();
        findBook();
        //deleteBook();
        //updateBook();
        return super.onStartCommand(intent, flags, startId);
    }

    private void findBook() {
        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                mBookDao.findBookByAuthor("张三");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void deleteBook() {
        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                mBookDao.deleteBookByAuthor("张三");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void updateBook() {
        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                mBookDao.updateBookByName("走江湖", "斗鱼xx");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void addBook() {
        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                mBookDao.addBook("创四方", "王五");
                mBookDao.addBook("闯天涯", "张三");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
