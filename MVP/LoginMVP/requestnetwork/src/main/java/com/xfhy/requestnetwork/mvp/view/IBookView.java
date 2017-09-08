package com.xfhy.requestnetwork.mvp.view;

import com.xfhy.requestnetwork.adapter.BookAdapter;
import com.xfhy.requestnetwork.bean.Book;

import java.util.List;

/**
 * Created by xfhy on 2017/7/22.
 */

public interface IBookView {

    void showLoading();

    void hideLoading();

    void updateAdapter(List<Book> bookList);

    void showFaileError(String error);

}
