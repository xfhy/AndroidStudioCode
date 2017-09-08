package com.xfhy.webviewtest;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private WebView mWebView;
    private FloatingActionButton mExit;

    @Override
    protected void onResume() {
        if (mWebView != null) {
            //激活WebView为活跃状态，能正常执行网页的响应
            mWebView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    private void initView() {

        /*
        注意事项：如何避免WebView内存泄露？
        不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用
         */
       /* CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        addContentView(mWebView, params);*/


        mWebView = (WebView) findViewById(R.id.wv_test);
        mExit = (FloatingActionButton) findViewById(R.id.fab_exit);
        mExit.setOnClickListener(this);

        WebSettings settings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式


        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);

        mWebView.loadUrl("http://www.baidu.com");
    }

    //在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击返回后，是网页回退而不是推出浏览器
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 销毁WebView
     */
    private void destroyWebView() {
        /*
        注意事项：如何避免WebView内存泄露？
        在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
         */
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: ");
        Snackbar.make(v,"确定退出?",Snackbar.LENGTH_LONG).setAction("Exit", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();

    }

    /**
     * WebViewClient
     */
    private class MyWebViewClient extends WebViewClient {

        //覆写shouldOverrideUrlLoading方法,使得打开网页时不调用系统浏览器,而是在本View中显示
        //This method was deprecated in API level 24.
        //Use shouldOverrideUrlLoading(WebView, WebResourceRequest) instead.
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.e(TAG, "shouldOverrideUrlLoading: ");
            return true;
        }

        //作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //设定加载开始的操作
            super.onPageStarted(view, url, favicon);
            Log.e(TAG, "onPageStarted: ");
        }

        //作用：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
        @Override
        public void onPageFinished(WebView view, String url) {
            //设定加载结束的操作
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished: ");
        }

        //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
        @Override
        public void onLoadResource(WebView view, String url) {
            //设定加载资源的操作
            super.onLoadResource(view, url);
        }

        //This method was deprecated in API level 23.
        //Use onReceivedError(WebView, WebResourceRequest, WebResourceError) instead.
        //加载页面的服务器出现错误时（如404）调用。
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            Log.e(TAG, "onReceivedError: ");
            /*
            App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面就显得很丑陋了，那么这个时候我们的app
            就需要加载一个本地的错误提示页面，即webview如何加载一个本地的页面
            //步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
            //步骤2：将该html文件放置到代码根目录的assets文件夹下

            //步骤3：复写WebViewClient的onRecievedError方法
            //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
             */
            switch (errorCode) {
                case HttpStatus.SC_NOT_FOUND:
                    view.loadUrl("file:///android_assets/error_handle.html");
                    break;
            }
        }

        //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }
    }
}


