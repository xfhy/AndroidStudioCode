package com.xfhy.requestnetwork.mvp.presenter;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.xfhy.requestnetwork.adapter.BookAdapter;
import com.xfhy.requestnetwork.bean.Book;
import com.xfhy.requestnetwork.mvp.model.OnRequestListener;
import com.xfhy.requestnetwork.mvp.model.impl.RequestNetwork;
import com.xfhy.requestnetwork.mvp.view.IBookView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/7/22.
 */

public class RequestPresenter implements OnRequestListener {

    private static final String TAG = "RequestPresenter";
    private static final String BOOK_URL = "http://xfhy-json.oss-cn-shanghai.aliyuncs" +
            ".com/get_data.json";
    private IBookView mBookView;
    private RequestNetwork mRequest;
    private Handler mHandler = new Handler();

    public RequestPresenter(IBookView mBookView) {
        this.mBookView = mBookView;
        mRequest = new RequestNetwork();
    }

    public void requestBooks() {
        mBookView.showLoading();
        mRequest.request(BOOK_URL, this);
    }

    @Override
    public void onSuccess(String result) {
        //解析json数据  更新适配器  显示

        try {
            analyJson(result);
        } catch (JSONException e) {
            e.printStackTrace();

            //显示错误信息
            onError(e.getCause().getLocalizedMessage());
        }

        Log.e(TAG, "onSuccess: " + result);

        //隐藏进度条
        mBookView.hideLoading();
    }

    /**
     * 解析json数据
     *
     * @param result
     */
    private void analyJson(String result) throws JSONException {
        if (TextUtils.isEmpty(result)) {
            return;
        }
        JSONArray bookArray = new JSONArray(result);
        int bookCount = bookArray.length();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            JSONObject jsonObject = bookArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String version = jsonObject.getString("version");
            String name = jsonObject.getString("name");

            Book book = new Book();
            book.setId(id);
            book.setVersion(version);
            book.setName(name);

            bookList.add(book);
        }
        mBookView.updateAdapter(bookList);
    }

    @Override
    public void onError(String error) {
        Log.e(TAG, "onError: " + error);
        mBookView.hideLoading();
    }
}
