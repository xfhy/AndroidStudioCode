package com.xfhy.fourcompnentdemo.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.xfhy.fourcompnentdemo.util.ToastUtil;

/**
 * @author feiyang
 *         time create at 2017/11/28 13:32
 *         description
 */
public class BookSQLiteHelpler extends SQLiteOpenHelper {

    private final static String BOOK_DB_NAME = Environment.getExternalStorageDirectory()
            + "/xfhy/book.db";
    private final static String BOOK_DB_CREATE = "create table if not exists book(" +
            "bookid integer primary key autoincrement," +
            "name varchar(50)," +
            "author varchar(20))";


    public BookSQLiteHelpler(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version) {
        /*
        * 构造方法中的参数介绍：
 //context :上下文   ， name：数据库文件的名称    factory：用来创建cursor对象，默认为null
 //version:数据库的版本号，从1开始，如果发生改变，onUpgrade方法将会调用,android 4.0之后只能升不能降
        * */
        super(context, BOOK_DB_NAME, factory, version);
    }

    public BookSQLiteHelpler(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version, DatabaseErrorHandler errorHandler) {
        super(context, BOOK_DB_NAME, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        *  oncreate方法是数据库第一次创建的时候会被调用; 特别适合做表结构的初始化,需要执行sql语句；SQLiteDatabase db可以用来执行sql语句
        * */
        db.execSQL(BOOK_DB_CREATE);
        ToastUtil.showMessage("已经创建数据库,目录:" + BOOK_DB_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade数据库版本号发生改变时才会执行； 特别适合做表结构的修改
        //这里升级数据库时,没有break语句,这是为了保证每一次的数据库修改都能被全部执行到.
        //比如我现在是数据库版本1,我安装了最新的软件,就需要升级到数据库版本3,就需要做1->2,2->3的所有操作
        switch (oldVersion) {
            case 1:  //如果原来的数据版本是1,则现在要升级数据库,需要建表
                db.execSQL(BOOK_DB_CREATE);  //创建Category表
            case 2:  //如果原来的数据库版本是2,则现在要增加Book表的属性,需要执行以下sql语句
                db.execSQL("alter table book add column category_id integer");
                ToastUtil.showMessage("数据库已升级");
            default:
                break;
        }
    }
}
