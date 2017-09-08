package com.xfhy.requestnetwork.mvp.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.xfhy.requestnetwork.R;
import com.xfhy.requestnetwork.adapter.BookAdapter;
import com.xfhy.requestnetwork.bean.Book;
import com.xfhy.requestnetwork.mvp.presenter.RequestPresenter;

import java.util.List;

public class BookActivity extends AppCompatActivity implements IBookView, View.OnClickListener {

    private static final String TAG = "BookActivity";
    private ProgressDialog mDialog;
    private RequestPresenter mPresenter;
    private Button btRequest;
    private ListView lvBook;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initView();
        mPresenter = new RequestPresenter(this);
    }

    private void initView() {
        btRequest = (Button) findViewById(R.id.btn_request);
        lvBook = (ListView) findViewById(R.id.lv_books);

        mAdapter = new BookAdapter(this, getData());
        lvBook.setAdapter(mAdapter);
        btRequest.setOnClickListener(this);
    }

    private List<Book> getData() {
        return null;
    }

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }

    @Override
    public void updateAdapter(List<Book> bookList) {
        Log.e(TAG, "updateAdapter: " + bookList.toString());
        mAdapter.updateBookData(bookList);
    }

    @Override
    public void showFaileError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request:
                //通过presenter请求网络数据
                mPresenter.requestBooks();
                break;
        }
    }
}
