package com.xfhy.mvprxjavaretrofit.service.presenter;

import android.content.Intent;

import com.xfhy.mvprxjavaretrofit.service.view.View;

/**
 * Created by xfhy on 2017/8/12.
 * 基础的Presenter
 *
 * 里面我们可以看到，定义了一些方法，前面几个onCreate、onStart等方法对应着Activity中生命周期的方法，当然没必要写上Activity生命周期中所有回调方法，通常也就用到了onCreate和onStop，除非需求很复杂，在Activity不同生命周期请求的情况不同。接着我们定义了一个attachView方法，用于绑定我们定义的View。也就是，你想把请求下来的数据实体类给哪个View就传入哪个View。下面这个attachIncomingIntent暂且没用到，就不说了。

 作者：蛇发女妖
 链接：http://www.jianshu.com/p/7b839b7c5884
 來源：简书
 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public interface BasePresenter {

    void onCreate();

    void onStart(); //暂时没用到

    void onStop();

    void onPause(); //暂时没用到

    void attachView(View view);

    void attachIncomingIntent(Intent intent);  //暂时没用到

}
