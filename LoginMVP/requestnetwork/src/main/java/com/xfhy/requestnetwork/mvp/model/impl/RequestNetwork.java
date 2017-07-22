package com.xfhy.requestnetwork.mvp.model.impl;

import com.xfhy.requestnetwork.mvp.model.IRequestNetwork;
import com.xfhy.requestnetwork.mvp.model.OnRequestListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by xfhy on 2017/7/22.
 */

public class RequestNetwork implements IRequestNetwork {

    @Override
    public void request(String url, final OnRequestListener listener) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (listener != null) {
                    ex.printStackTrace();
                    listener.onError(ex.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
