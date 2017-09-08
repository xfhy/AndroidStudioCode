package com.xfhy.mvprxjavaretrofit.service;

import com.xfhy.mvprxjavaretrofit.service.entity.Joke;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by xfhy on 2017/8/12.
 */

public interface RetrofitService {

    /**
     * 其实就是拼接字符串 比如
     * http://jisuxhdq.market.alicloudapi.com/xiaohua/text?pagenum=1&pagesize=1&sort=addtime
     * 将注解拼接起来,拼到url中
     *
     * @param authorization Authorization
     *                      请求Header中添加的Authorization字段；配置Authorization字段的值为“APPCODE ＋ 半角空格
     *                      ＋APPCODE值”。
     * @param pageNum       STRING	必选	页码
     * @param pageSize      STRING	必选	每页条数 最大20
     * @param sort          STRING	必选	排序 addtime按时间倒叙 rand随机获取 sort=rand时，pagenum无效
     * @return
     */
    @GET("/xiaohua/text")
    Observable<Joke> getJoke(@Header("Authorization") String authorization, @Query("pagenum") int
            pageNum, @Query("pagesize") int pageSize,
                             @Query("sort") String sort);

    /**
     * GET ----------查找资源（查）
     POST --------修改资源（改）
     PUT ----------上传文件（增）
     DELETE ----删除文件（删）
     HEAD--------只请求页面的首部
     */

}
