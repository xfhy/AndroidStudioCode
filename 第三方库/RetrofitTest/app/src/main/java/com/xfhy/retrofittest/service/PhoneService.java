package com.xfhy.retrofittest.service;

import com.xfhy.retrofittest.model.AddressResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by xfhy on 2017/8/5.
 */

public interface PhoneService {

    /*
    *   @Header用来添加Header
        @Query用来添加查询关键字
    * */

    @GET("/shouji/query")
    Call<AddressResponse> getResult(@Header("Authorization") String appCode, @Query("shouji") String
            phone);

}
