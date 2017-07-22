package com.xfhy.requestnetwork.mvp.model;

/**
 * Created by xfhy on 2017/7/22.
 */

public interface IRequestNetwork {

    void request(String url, OnRequestListener listener);

}
