package com.xfhy.rxjavanetworktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xfhy.rxjavanetworktest.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 2017年7月30日21:39:59
 * 使用RxJava请求网络,获取json数据并解析,并显示
 *
 * 简单来讲:
 使用RxJava，你可以使用subscribeOn()指定被观察者代码运行的线程，使用observerOn()指定订阅者运行的线程
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String URL = "http://xfhy-json.oss-cn-shanghai.aliyuncs.com/get_data.json";
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        acceptNetwork();
    }

    private void acceptNetwork() {
        Observable.create(new ObservableOnSubscribe<Response>() { //OkHttp返回的数据是Response对象
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                //线程RxCachedThreadScheduler-1
                Log.e(TAG, "subscribe:通过OkHttp访问网络 线程" + Thread.currentThread().getName());
                //通过OkHttp访问网络
                Request.Builder builder = new Request.Builder().url(URL).get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, List<Book>>() {  //map 将什么转换成什么
            @Override
            public List<Book> apply(@NonNull Response response) throws Exception {
                // map: 线程RxCachedThreadScheduler-1
                Log.e(TAG, "map: 线程" + Thread.currentThread().getName());
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String result = responseBody.string();
                        List<Book> bookList = AnalysisJson(result);
                        return bookList;
                    }
                }
                return null;
            }
        }).subscribeOn(Schedulers.io())  //切换到io线程   subscribeOn的调用切换之前的线程。
                .doOnNext(new Consumer<List<Book>>() {
                    @Override
                    public void accept(List<Book> bookList) throws Exception {
                        //线程RxCachedThreadScheduler-1
                        Log.e(TAG, "doOnNext: 线程" + Thread.currentThread().getName());
                        //在这里做数据保存
                        Log.e(TAG, "doOnNext: 数据保存成功");
                    }
                }).observeOn(AndroidSchedulers.mainThread())   //切换到主线程    observeOn的调用切换之后的线程。
                .subscribe(new Consumer<List<Book>>() {
                    @Override
                    public void accept(List<Book> bookList) throws Exception {
                        Log.e(TAG, "subscribe: 线程" + Thread.currentThread().getName());  //main
                        Log.e(TAG, "subscribe: 成功访问网络数据" + bookList.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe: 线程" + Thread.currentThread().getName());   //main
                        Log.e(TAG, "subscribe: 访问网络数据失败" + throwable.getMessage());
                        throwable.printStackTrace();
                    }
                })
        ;
    }

    /**
     * 解析json数据
     *
     * @param result
     * @return
     * @throws JSONException
     */
    private List<Book> AnalysisJson(String result) throws JSONException {
        List<Book> bookList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(result);
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Book book = new Book();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            book.setId(jsonObject.getInt("id"));
            book.setVersion(jsonObject.getDouble("version"));
            book.setName(jsonObject.getString("name"));
            bookList.add(book);
        }
        return bookList;

    }

    private void initView() {
        tvTest = (TextView) findViewById(R.id.tv_test);
    }
}
