package com.xfhy.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xfhy.litepaltest.bean.Book;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private Button bt_create;
    private Button bt_add;
    private Button bt_update;
    private Button bt_delete;
    private Button bt_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_create = (Button) findViewById(R.id.bt_create);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_query = (Button) findViewById(R.id.bt_query);

        bt_create.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_query.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_create:
                craeteDatabase();
                break;
            case R.id.bt_add:
                addData();
                break;
            case R.id.bt_update:
                updateData();
                break;
            case R.id.bt_delete:
                deleteData();
                break;
            case R.id.bt_query:
                queryData();
                break;
        }
    }

    /**
     * 查询数据
     */
    private void queryData() {
        List<Book> books = DataSupport.findAll(Book.class);
        for (Book book : books) {
            Log.i(TAG, "name: "+book.getName());
            Log.i(TAG, "author: "+book.getAuthor());
            Log.i(TAG, "press: "+book.getPress());
            Log.i(TAG, "id: "+book.getId());
            Log.i(TAG, "pages: "+book.getPages());
            Log.i(TAG, "price: "+book.getPrice());
        }

        /*//查询第一条数据
        Book firstBook = DataSupport.findFirst(Book.class);
        //查询book表的最后一条数据
        Book lastBook = DataSupport.findLast(Book.class);

        //查询book表的 指定的几列数据
        DataSupport.select("name","author").find(Book.class);

        //查询book表的   指定的约束条件  数据
        DataSupport.where("pages > ?","400").find(Book.class);

        //查询book表的  指定结果的排序方式
        DataSupport.order("price desc").find(Book.class);  //desc是降序     asc或者不写是升序

        //查询book表的  指定查询结果数量
        DataSupport.limit(3).find(Book.class);

        //指定查询结果的偏移量
        DataSupport.offset(3).find(Book.class);
        DataSupport.limit(3).offset(3).find(Book.class);  //查询第4,5,6条数据

        //组合查询
        DataSupport.select("name","author","pages")
                .where("pages > ?","400")
                .order("pages")
                .limit(10)
                .offset(10)
                .find(Book.class);

        //用原生SQL语句进行查询
        DataSupport.findBySQL("select * from Book where pages > ? and price < ?","400","20");*/

    }

    /**
     * 删除数据
     */
    private void deleteData() {
        //删除数据   不传参则删除全部
        //参数:表名.class   where占位符   占位符的值
        DataSupport.deleteAll(Book.class,"price < ?","20");
    }


    /**
     * 更新数据库
     */
    private void updateData() {
        Book book = new Book();
        book.setAuthor("你猜");   //更新为xx
        book.setPages(222);

        //这里和SqLiteDatabase的update()方法的where参数部分有点类似,但更简洁
        book.updateAll("name= ? and author = ?","数学书","晓峰哈虐");
    }

    /**
     * 添加数据
     */
    private void addData() {
        //新建一个实体类,放好数据,调用save()即可插入数据库
        Book book = new Book();
        book.setName("语文书");
        book.setAuthor("晓峰哈虐");
        book.setPages(454);
        book.setPrice(45);
        book.setPress("三和");

        //这里会重复保存,如果多点击一次Add按钮
        boolean result = book.save();   //保存到数据库  成功则返回true
        if(result){
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建数据库
     */
    private void craeteDatabase() {
        //return : A writable SQLiteDatabase instance
        LitePal.getDatabase();   //创建数据库
    }
}
