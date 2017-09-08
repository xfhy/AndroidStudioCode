package com.xfhy.requestnetwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xfhy.requestnetwork.R;
import com.xfhy.requestnetwork.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/7/22.
 */

public class BookAdapter extends BaseAdapter {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList != null ? bookList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (bookList != null && bookList.size() > position) {
            Book book = bookList.get(position);
            return book;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (bookList != null && bookList.size() > position) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_book, null);
                viewHolder = new ViewHolder();
                viewHolder.tvBookId = (TextView) view.findViewById(R.id.tv_book_id);
                viewHolder.tvBookVersion = (TextView) view.findViewById(R.id.tv_book_version);
                viewHolder.tvBookName = (TextView) view.findViewById(R.id.tv_book_name);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            Book book = bookList.get(position);
            viewHolder.tvBookId.setText(book.getId()+"");
            viewHolder.tvBookVersion.setText(book.getVersion());
            viewHolder.tvBookName.setText(book.getName());
            return view;
        }
        return null;
    }

    private class ViewHolder {
        TextView tvBookName;
        TextView tvBookId;
        TextView tvBookVersion;
    }

    /**
     * 更新数据
     *
     * @param dataList
     */
    public void updateBookData(List<Book> dataList) {
        if (dataList == null) {
            return;
        }
        if (dataList.size() == 0) {
            return;
        }
        if (bookList == null) {
            bookList = new ArrayList<>();
        }

        bookList.clear();
        bookList.addAll(dataList);
        this.notifyDataSetChanged();
    }
}
