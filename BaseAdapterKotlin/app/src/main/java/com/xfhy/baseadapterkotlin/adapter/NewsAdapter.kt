package com.a517na.feiyang.baseadapterpackage.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xfhy.baseadapterkotlin.R
import com.xfhy.baseadapterkotlin.model.News

/**
 * description：RecyclerView的adapter
 * author feiyang
 * create at 2017/8/3 17:41
 */
class NewsAdapter : RecyclerView.Adapter
<NewsAdapter.ViewHolder> {

    private var context: Context? = null
    private var newsList: ArrayList<News>? = null

    constructor(context: Context, newsList: ArrayList<News>) {
        this.context = context
        this.newsList = newsList
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var ivDes: ImageView
        var tvTitle: TextView

        constructor(itemView: View) : super(itemView) {
            ivDes = itemView.findViewById(R.id.iv_news_des) as ImageView
            tvTitle = itemView.findViewById(R.id.tv_news_title) as TextView
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (newsList?.size as Int > position) {
            val news = newsList?.get(position)
            Glide.with(context).load(news?.thumbnailPicS).into(holder?.ivDes)
            holder?.tvTitle?.text = news?.title
        }
    }

    override fun getItemCount(): Int {
        return newsList?.size as Int
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    fun addData(dataList: ArrayList<News>): Unit {
        if (dataList.size == 0) {
            return
        }
        newsList?.clear()
        newsList?.addAll(dataList)

        //更新UI
        notifyDataSetChanged()
    }

}