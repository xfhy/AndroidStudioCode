package com.xfhy.baseadapterkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.a517na.feiyang.baseadapterpackage.adapter.NewsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xfhy.baseadapterkotlin.model.News
import com.xfhy.baseadapterkotlin.model.NewsResponse
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var newsAdapter: NewsAdapter? = null
    val client = OkHttpClient()
    val NEWS_URL: String = "http://xfhy-json.oss-cn-shanghai.aliyuncs.com/news.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initData() {

        //请求网络
        Observable.create(ObservableOnSubscribe<String> {
            e ->
            //用OkHttp3访问网络
            val builder = Request.Builder()
            val request = builder.url(NEWS_URL).get().build()
            val response = client.newCall(request).execute()
            val responseBody = response.body()
            val result = responseBody?.string()
            Log.e(TAG, result)

            //当形参为String类型,而传递的实参是String?类型时在后面加!!可以解决
            e.onNext(result!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    e -> Log.e(TAG, e)

                    //用Gson解析
                    val newsType = object : TypeToken<NewsResponse>() {}.type
                    val newsResponse = Gson().fromJson<NewsResponse>(e, newsType)
                    val arrayList = newsResponse.result?.data

                    //更新适配器数据
                    newsAdapter?.addData(arrayList as ArrayList<News>)
                })
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_news.layoutManager = linearLayoutManager

        newsAdapter = NewsAdapter(this, getData())
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
