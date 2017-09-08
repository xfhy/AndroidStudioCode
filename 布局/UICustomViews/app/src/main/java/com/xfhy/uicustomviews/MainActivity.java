package com.xfhy.uicustomviews;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private Button bt_back;
    private Button bt_edit;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取标题栏  并且隐藏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }



        bt_back = (Button) findViewById(R.id.bt_back);
        bt_edit = (Button)findViewById(R.id.bt_edit);
        et_content = (EditText)findViewById(R.id.et_content);

        bt_back.setOnClickListener(this);
        bt_edit.setOnClickListener(this);

        if(savedInstanceState != null){
            Log.i(TAG, "onCreate: 恢复临时数据");
            et_content.setText(savedInstanceState.getString("content"));
        }
    }

    //当Activity临时切换到了后台时,用这个方法保存数据 (eg:接电话   接完电话回到这个界面之前的数据还在)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String content = et_content.getText().toString();
        if(!TextUtils.isEmpty(content)){
            outState.putString("content",content);
            Log.i(TAG, "onSaveInstanceState: 保存临时数据");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                Log.i(TAG, "onClick: 返回键被点击了");
                finish();
                break;
            case R.id.bt_edit:
                if(bt_edit.getText().equals("Edit")){
                    bt_edit.setText("Save");
                } else {
                    bt_edit.setText("Edit");
                }
                break;
        }
    }
}
