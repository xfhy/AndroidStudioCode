package com.xfhy.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.xfhy.news.adapter.NewsAdapter
import com.xfhy.news.bean.HotNews
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    val wx_url = "http://jisuwxwzjx.market.alicloudapi.com/weixinarticle/get?channelid=1"

    val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initData() {
        //这是anko里面的,异步执行
        async {
            val okHttpClient = OkHttpClient().newBuilder().build()
            val request = Request.Builder().url(wx_url).header("Authorization",
                    "APPCODE d31030b7e58b409a91bfc43aba06658b").build()
            val newCall = okHttpClient.newCall(request)
            val execute = newCall.execute()
            if (execute.isSuccessful) {
                val result = execute.body()?.string()
                val gson = Gson()
                val hotNews = gson.fromJson<HotNews>(result, HotNews::class.java)
                Log.e("tag", "fromJson=" + hotNews)
                //回到主线程
                uiThread {
                    newsAdapter.setData(hotNews.result.list)
                }
            }
        }
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvNewsList.layoutManager = linearLayoutManager
        rvNewsList.adapter = newsAdapter
    }
}
