package com.a517na.feiyang.baseadapterpackage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.a517na.feiyang.baseadapterpackage.adapter.NewsAdapter
import com.a517na.feiyang.baseadapterpackage.model.News
import com.a517na.feiyang.baseadapterpackage.model.NewsResponse
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.function.Consumer


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val client = OkHttpClient()
    val NEWS_URL: String = "http://xfhy-json.oss-cn-shanghai.aliyuncs.com/news.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initData() {
        Observable.create(ObservableOnSubscribe<String> {
            e ->

            val builder = Request.Builder()
            val request = builder.url(NEWS_URL).get().build()
            val response = client.newCall(request).execute()
            val responseBody = response.body()
            val result = responseBody?.string()
            Log.e(TAG, result)
            e.onNext(result!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    e -> Log.e(TAG, e)

                })
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_news.layoutManager = linearLayoutManager

        val newsAdapter = NewsAdapter(this, getData())
        rv_news.adapter = newsAdapter

        //设置分割线
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        rv_news.addItemDecoration(dividerItemDecoration)
    }

    private fun getData(): ArrayList<News> {
        val newsList = ArrayList<News>()
        /*newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))
        newsList.add(News(null, "你是猴子派来的逗比吗?", "123455"))*/
        return newsList
    }
}
