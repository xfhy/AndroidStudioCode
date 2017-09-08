package com.xfhy.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.retrofittest.model.AddressResponse;
import com.xfhy.retrofittest.model.Result;
import com.xfhy.retrofittest.service.PhoneService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String APP_CODE = "APPCODE d31030b7e58b409a91bfc43aba06658b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestData();
    }

    private void requestData() {
        /*
        *   创建Retrofit对象
            创建访问API的请求
            发送请求
            处理结果
        * */

        //1, 创建REtrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jshmgsdmfb.market.alicloudapi.com")  //主机地址
                .addConverterFactory(GsonConverterFactory.create())  //解析方法
                .build();

        //2,创建访问API的请求
        PhoneService service = retrofit.create(PhoneService.class);
        Call<AddressResponse> call = service.getResult(APP_CODE,"13456755448");

        //3, 发送请求
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                //4, 处理结果
                if (response.isSuccessful()) {
                    //这里获取的结果是已经解析完成了的
                    AddressResponse result = response.body();
                    if (result != null) {
                        Result resultData = result.getResult();
                        Log.e(TAG, "onResponse: "+resultData.toString());
                    }
                } else {
                    Log.e(TAG, "onResponse: 请求数据失败");
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage());
                t.printStackTrace();
            }
        });

    }
}
