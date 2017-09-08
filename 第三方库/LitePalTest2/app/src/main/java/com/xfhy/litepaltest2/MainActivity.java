package com.xfhy.litepaltest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xfhy.litepaltest2.model.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button btAdd;
    private Button btDelete;
    private Button btUpdate;
    private Button btQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        btAdd = (Button) findViewById(R.id.bt_add);
        btDelete = (Button) findViewById(R.id.bt_delete);
        btUpdate = (Button) findViewById(R.id.bt_update);
        btQuery = (Button) findViewById(R.id.bt_query);


        btAdd.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                addBook();
                break;
            case R.id.bt_delete:
                deleteBook();
                break;
            case R.id.bt_update:
                updateBook();
                break;
            case R.id.bt_query:
                queryBook();
                break;
        }
    }

    private void queryBook() {
        List<Book> bookList = DataSupport.findAll(Book.class);
        if (bookList != null && bookList.size() > 0) {
            for (Book book : bookList) {
                Log.e(TAG, "queryBook: " + book.toString());
            }
        }
    }

    private void updateBook() {
        Book book = DataSupport.find(Book.class, 1);
        if (book != null) {
            book.setName("我是修改之后的....");
            book.save();
            Log.e(TAG, "updateBook: 修改之后是" + book.toString());
        }
    }

    private void deleteBook() {
        int id = DataSupport.delete(Book.class, 1);
        if (id != 0) {
            Log.e(TAG, "deleteBook: 删除成功   id为" + id);
            Toast.makeText(this, id + " 删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void addBook() {
        Book book = new Book();
        book.setId(6);
        book.setAuthor("xfhy");
        book.setName("你是熊孩子");
        book.setPages(57);
        book.setPress("dadasdas");
        boolean save = book.save();//保存
        if (save) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }
}
