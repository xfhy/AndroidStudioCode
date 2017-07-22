package com.xfhy.loginmvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xfhy.loginmvp.R;
import com.xfhy.loginmvp.presenter.LoginPresenter;

/**
 * MVP的demo
 *
 * @author xfhy
 *         create at 2017年7月1日15:26:53
 *         Controller/Presenter负责逻辑的处理，Model提供数据，View负责显示。
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private EditText mUserName;
    private EditText mUserPass;
    private Button mLogin;
    private Button mClear;
    private ProgressBar mProgress;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        mPresenter = new LoginPresenter(this);
    }

    private void initView() {
        mUserName = (EditText) findViewById(R.id.et_user_name);
        mUserPass = (EditText) findViewById(R.id.et_user_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mClear = (Button) findViewById(R.id.btn_clear);
        mProgress = (ProgressBar) findViewById(R.id.pb_login);

        mLogin.setOnClickListener(this);
        mClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mPresenter.login();
                break;
            case R.id.btn_clear:
                mPresenter.clear();
                break;
        }
    }

    @Override
    public String getUserName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getUserPass() {
        return mUserPass.getText().toString();
    }

    @Override
    public void clearUserName() {
        mUserName.setText("");
    }

    @Override
    public void clearUserPass() {
        mUserPass.setText("");
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity() {
        //跳转到登录成功的界面
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFaileError(Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
