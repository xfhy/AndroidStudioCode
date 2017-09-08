package com.xfhy.baseadapterkotlin.model

import com.google.gson.annotations.SerializedName
import com.xfhy.baseadapterkotlin.model.Data

/**
 * description：
 * author feiyang
 * create at 2017/8/3 19:46
 */
class NewsResponse {

    @SerializedName("error_code")
    var errorCode : String? = null
    @SerializedName("reason")
    var reason: String? = null
    @SerializedName("result")
    var result: Data? = null

}