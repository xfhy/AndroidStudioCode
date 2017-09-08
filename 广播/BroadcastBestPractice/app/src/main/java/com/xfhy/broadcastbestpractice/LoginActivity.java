package com.xfhy.broadcastbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText et_account;
    private EditText et_password;
    private Button bt_login;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        //1. 找到控件
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);

        //2. 监听
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = et_account.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                //3. 判断是否登录成功
                if(account.equals("xfhy") && password.equals("123")){
                    Toast.makeText(mContext, "登录成功!", Toast.LENGTH_SHORT).show();
                    //开启主界面
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();   //销毁自己
                } else {
                    Toast.makeText(mContext, "登录失败!请检查用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
