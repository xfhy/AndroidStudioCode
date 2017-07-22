package com.xfhy.mvptest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xfhy.mvptest.R;
import com.xfhy.mvptest.presenter.UserPresenter;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, IUserView {

    private EditText idText;
    private EditText mFirstName;
    private EditText mLastName;
    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
        mPresenter = new UserPresenter(this);
    }

    private void initView() {
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.load).setOnClickListener(this);
        idText = (EditText) findViewById(R.id.id);
        mFirstName = (EditText) findViewById(R.id.first);
        mLastName = (EditText) findViewById(R.id.last);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                mPresenter.saveUser(getID(), getFirstName(), getLastName());
                break;
            case R.id.load:
                mPresenter.loadUser(getID());
                break;
        }
    }

    @Override
    public int getID() {
        int id = -1;
        try {
            id = Integer.parseInt(this.idText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public String getFirstName() {
        return mFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return mLastName.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        mFirstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        mLastName.setText(lastName);
    }
}
