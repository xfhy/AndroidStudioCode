package com.a517na.feiyang.baseadapterpackage.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.a517na.feiyang.baseadapterpackage.R
import com.a517na.feiyang.baseadapterpackage.model.News

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
            ivDes = itemView.findViewById(R.id.iv_news_des)
            tvTitle = itemView.findViewById(R.id.tv_news_title)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (newsList?.size as Int > position) {
            val news = newsList?.get(position)
            //holder?.ivDes?.setImageURI(Uri.parse(news?.content))
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
}