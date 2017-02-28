package com.xfhy.networktest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xfhy.networktest.model.App;
import com.xfhy.networktest.util.StreamUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用HttpURLConnection与服务器请求,将服务器返回的数据显示出来
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button bt_send_request;
    private TextView tv_response_text;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;
            tv_response_text.setText(content);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_send_request = (Button) findViewById(R.id.bt_send_request);
        tv_response_text = (TextView) findViewById(R.id.tv_response_text);

        bt_send_request.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send_request:
//                sendRequestWithHttpURLConnection();
                sendRequestWithOkHttp();
                break;
        }
    }

    /**
     * 发送网络请求 用HttpURLConnection    GET方式
     * 这个需要放在子线程中操作
     * 子线程不能更新UI
     */
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection urlConnection = null;
                try {
                    //1. 创建一个URL对象
                    url = new URL("http://www.baidu.com");
                    //2. 打开连接  获得HttpURLConnection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //3. 设置HTTP请求的方式
                    urlConnection.setRequestMethod("GET");
                    //4. 设置一些参数   连接超时时间    读取超时时间
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    //5. 在获取url请求的数据前需要判断响应码，200 ：成功,206:访问部分数据成功
                    // 300：跳转或重定向  400：错误 500：服务器异常
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        //6. 获取服务器返回的输入流
                        InputStream inputStream = urlConnection.getInputStream();
                        //7. 将输入量解析出来
                        String result = StreamUtils.streamToString(inputStream);

                        //8. 通过Handler更新UI
                        Message msg = Message.obtain();
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //9. 最后记得断开连接
                    if (urlConnection != null){
                        urlConnection.disconnect();//关闭http连接
                    }
                }
            }
        }).start();
    }

    /**
     * 发送网络请求  用OkHttp    GET方式
     */
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1. 首先创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //2. 创建Request对象
                    Request request = new Request.Builder()
                            .url("http://10.0.3.2/get_data.json")   //设置目标地址URL
                            .build();
                    //3.获取服务器返回的数据    就是这个Response对象
                    Response response = client.newCall(request).execute();
                    //4. 将数据弄出来
                    String responseData = response.body().string();

//                    parseJSONWithJSONObject(responseData);
                    parseJSONWithGSON(responseData);

                    //5 . 通过Handler更新UI
                    Message msg = Message.obtain();
                    msg.obj = responseData;
                    handler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析JSON数据
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData){
        /**
         * 服务端数据为
         * [{"id":"5","version":"5.5","name":"Clash of Clans"},
         {"id":"6","version":"7.0","name":"Boom Beach"},
         {"id":"7","version":"3.5","name":"Clash Royale"}
         ]
         */
        try {
            //1. 创建JSONArray   里面是数组
            JSONArray jsonArray = new JSONArray(jsonData);
            //2. 解析数组里面的每一个数据
            for (int i=0; i<jsonArray.length(); i++){
                //3. 一个数组元素就是一个JSONObject
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //4. 根据key获取对应的值
                String id = jsonObject.getString("id");
                String version = jsonObject.getString("version");
                String name = jsonObject.getString("name");

                Log.i(TAG, "parseJSONWithJSONObject: id"+id);
                Log.i(TAG, "parseJSONWithJSONObject: version"+version);
                Log.i(TAG, "parseJSONWithJSONObject: name"+name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析JSON  使用GSON
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for (App app : appList) {
            Log.i(TAG, "parseJSONWithGSON: "+app.getId());
            Log.i(TAG, "parseJSONWithGSON: "+app.getName());
            Log.i(TAG, "parseJSONWithGSON: "+app.getVersion());
        }
    }

}
