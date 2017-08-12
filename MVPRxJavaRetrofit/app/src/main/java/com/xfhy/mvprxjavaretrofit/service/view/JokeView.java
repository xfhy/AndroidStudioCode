package com.xfhy.mvprxjavaretrofit.service.view;

import com.xfhy.mvprxjavaretrofit.service.entity.Joke;

/**
 * Created by xfhy on 2017/8/12.
 * Joke模块的View  下面2个方法比较常见,然你可以根据项目需要来定义一些其他的方法
 */

public interface JokeView extends View{
    /**
     * 请求成功
     * @param joke 需要显示的实体类
     */
    void onSuccess(Joke joke);

    /**
     * 请求失败
     * @param result  错误信息
     */
    void onError(String result);
}
