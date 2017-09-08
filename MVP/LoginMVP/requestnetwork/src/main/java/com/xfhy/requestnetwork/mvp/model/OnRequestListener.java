package com.xfhy.requestnetwork.mvp.model;

/**
 * Created by xfhy on 2017/7/22.
 */

public interface OnRequestListener {

    void onSuccess(String result);

    void onError(String error);

}
