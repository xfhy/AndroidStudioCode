package com.xfhy.mvprxjavaretrofit.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.xfhy.mvprxjavaretrofit.R;
import com.xfhy.mvprxjavaretrofit.service.entity.Joke;
import com.xfhy.mvprxjavaretrofit.service.presenter.JokePresenter;
import com.xfhy.mvprxjavaretrofit.service.view.JokeView;

public class MainActivity extends AppCompatActivity implements JokeView, View.OnClickListener {

    private JokePresenter mPresenter;
    private Button requestNetBtn;
    private TextView contentTv;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter();
        initView();
    }

    private void initView() {
        requestNetBtn = (Button) findViewById(R.id.bt_request_net);
        contentTv = (TextView) findViewById(R.id.tv_json);

        requestNetBtn.setOnClickListener(this);
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        mPresenter = new JokePresenter(this);
        mPresenter.onCreate();
        mPresenter.attachView(this);
    }

    @Override
    public void onSuccess(Joke joke) {
        contentTv.setText(joke.toString());
    }

    @Override
    public void onError(String result) {
        contentTv.setText(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_request_net:
                mPresenter.getJoke("APPCODE d31030b7e58b409a91bfc43aba06658b", 1, 20, "addtime");
                break;
        }
    }
}
