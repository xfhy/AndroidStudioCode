package com.xfhy.fourcompnentdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xfhy.fourcompnentdemo.AppApplication;
import com.xfhy.fourcompnentdemo.BuildConfig;
import com.xfhy.fourcompnentdemo.util.ToastUtil;

/**
 * @author feiyang
 *         time create at 2017/11/28 13:40
 *         description
 */
public class BookDao {

    private static final String TAG = "BookDao";

    private static BookDao instance;
    private final SQLiteDatabase mReadableDatabase;

    private BookDao() {
        BookSQLiteHelpler bookSQLiteHelpler = new BookSQLiteHelpler(AppApplication.getAppContext
                (), "", null, Integer.valueOf(BuildConfig.DB_VERSION));
        mReadableDatabase = bookSQLiteHelpler.getReadableDatabase();
    }

    public static BookDao getInstance() {
        BookDao result = instance;
        if (result == null) {
            synchronized (BookDao.class) {
                result = instance;
                if (result == null) {
                    instance = result = new BookDao();
                }
            }
        }
        return result;
    }

    public void findBookByAuthor(String author) {
        Cursor cursor = mReadableDatabase.query("book", null, "author=?", new String[]{author},
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Log.e(TAG, "name=" + name);
                ToastUtil.showMessage("查找" + name + "成功");
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void deleteBookByAuthor(String author) {
        mReadableDatabase.delete("book", "author=?", new String[]{author});
    }

    public void updateBookByName(String name, String author) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("author", author);
        mReadableDatabase.update("book", contentValues, "name=?", new String[]{name});
    }

    public void addBook(String name, String author) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("author", author);
        long insertRow = mReadableDatabase.insert("book", null, contentValues);

    }


}
