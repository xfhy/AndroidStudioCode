package com.xfhy.interview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xfhy.interview.entity.Person;


public class ItemActivity extends Activity implements View.OnClickListener{

    private TextView personName;
    private TextView telephone;
    private ImageButton mCall;
    private ImageButton mSms;

    //person用于获取传过来的信息
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item);
        
        initView();
        
        initEvent();
    }

    private void initView() {
        personName = (TextView) findViewById(R.id.activity_item_personName);
        telephone = (TextView) findViewById(R.id.activity_item_telephone);
        mCall = (ImageButton) findViewById(R.id.activity_item_call);
        mSms = (ImageButton) findViewById(R.id.activity_item_sms);
    }

    private void initEvent() {
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("person");

        personName.setText(person.getName());
        telephone.setText(person.getTelephone());

        mCall.setOnClickListener(this);
        mSms.setOnClickListener(this);
    }

    /**
     * 拨打电话按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_item_call:
                Intent intent_call = new Intent(Intent.ACTION_DIAL);
                intent_call.setData(Uri.parse("tel:"+person.getTelephone()));
                startActivity(intent_call);
                break;
            case R.id.activity_item_sms:
                Uri uri2 = Uri.parse("smsto:"+person.getTelephone());
                Intent intentMessage = new Intent(Intent.ACTION_VIEW,uri2);
                startActivity(intentMessage);
                break;
            default:
        }
    }
}
