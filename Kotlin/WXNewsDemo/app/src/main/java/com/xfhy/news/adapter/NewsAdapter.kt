package com.xfhy.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.xfhy.news.R
import com.xfhy.news.bean.News
import com.xfhy.news.extensions.showSnackBar
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * @author xfhy
 * create at 2017/12/23 16:38
 * description：
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    val newsList by lazy {
        ArrayList<News>()
    }

    class NewsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.setOnClickListener { v ->
            v.showSnackBar("点我了 position=$position")
        }
        Glide.with(holder.itemView).load(newsList[position].pic).into(holder.itemView.iv_news_img)
        holder.itemView.tv_news_src.text = newsList[position].weiXinName
        holder.itemView.tv_news_title.text = newsList[position].title
        holder.itemView.tv_time.text = newsList[position].time
    }

    override fun getItemCount(): Int = newsList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    fun setData(newDatas: List<News>) {
        newsList.clear()
        newsList.addAll(newDatas)
        notifyDataSetChanged()
    }

}