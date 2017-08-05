package com.xfhy.baseadapterkotlin.model

import com.google.gson.annotations.SerializedName

/**
 * description：
 * author feiyang
 * create at 2017/8/3 17:37
 */
data class News(

        @SerializedName("author_name")
        var authorName: String?,
        @SerializedName("category")
        var category: String?,
        @SerializedName("date")
        var date: String?,
        @SerializedName("thumbnail_pic_s")
        var thumbnailPicS: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("uniquekey")
        var uniquekey: String?,
        @SerializedName("url")
        var url: String?

)
