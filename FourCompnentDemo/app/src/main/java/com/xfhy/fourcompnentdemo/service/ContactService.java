package com.xfhy.fourcompnentdemo.service;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xfhy.fourcompnentdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author feiyang
 *         time create at 2017/11/28 11:33
 *         description
 */
public class ContactService extends Service {
    private static final String TAG = "ContactService";
    private ContentResolver mContentResolver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "ContactService 调用onStartCommand()", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onStartCommand()");

        //获取ContentResolver对象   通过该对象可以查询系统联系人数据库
        mContentResolver = getContentResolver();
        findAllContact();
        //addContact();
        //deleteContact();
        //updateContact();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        super.onCreate();
    }

    private void findAllContact() {
        Flowable.create(new FlowableOnSubscribe<HashMap<String, String>>() {
            @Override
            public void subscribe(FlowableEmitter<HashMap<String, String>> e) throws Exception {
                Cursor cursor = mContentResolver.query(ContactsContract.CommonDataKinds.Phone
                                .CONTENT_URI,
                        null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    Log.e(TAG, "联系人有数据");
                    HashMap<String, String> contactMap = new HashMap<>(20);
                    do {
                        //联系人姓名
                        String contactName = cursor.getString(cursor.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        //获取联系人号码
                        String contactPhone = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (!TextUtils.isEmpty(contactName) && !TextUtils.isEmpty(contactPhone)) {
                            contactMap.put(contactPhone, contactName);
                        }
                    } while (cursor.moveToNext());
                    cursor.close();
                    e.onNext(contactMap);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HashMap<String, String>>() {
                    @Override
                    public void accept(HashMap<String, String> stringStringHashMap) throws
                            Exception {
                        Log.e(TAG, stringStringHashMap.toString());
                        ToastUtil.showMessage(stringStringHashMap.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void deleteContact() {

        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                Cursor cursor = mContentResolver.query(uri, new String[]{ContactsContract.Data
                        ._ID}, "display_name=?", new
                        String[]{"ghh"}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int rawId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
                    //根据id删除raw_contacts中的相应数据
                    mContentResolver.delete(uri, "display_name=?", new String[]{"ghh"});
                    //根据id删除data中的相应数据
                    uri = Uri.parse("content://com.android.contacts/data");
                    int deleteRows = mContentResolver.delete(uri, "raw_contact_id=?", new
                            String[]{rawId + ""});
                    if (deleteRows > 0) {
                        ToastUtil.showMessage("删除成功");
                    }
                    cursor.close();
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


    }

    private void updateContact() {
        Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                //首先查raw_contacts中的对应联系人的id
                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                Cursor cursor = mContentResolver.query(uri, new String[]{ContactsContract.Data
                        ._ID}, "display_name=?", new
                        String[]{"zdong"}, null);
                if (cursor != null && cursor.moveToFirst()) {  //这里可能存在很多个,为了测试,就只改一个
                    int rawId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
                    uri = Uri.parse("content://com.android.contacts/data");
                    ContentValues values = new ContentValues();
                    values.put("data1", "222222222");
                    //只需要更新data表   再更新该id的相应的元组数据
                    int updateRows = mContentResolver.update(uri, values, "mimetype=? and " +
                            "raw_contact_id=?", new
                            String[]{"vnd.android.cursor.item/phone_v2", rawId + ""});
                    if (updateRows > 0) {
                        ToastUtil.showMessage("更新成功");
                    }
                    cursor.close();
                }


            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void stringStringHashMap) throws
                            Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void addContact() {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentValues values = new ContentValues();
        //raw_contacts 表   得到contact_id
        long contact_id = ContentUris.parseId(mContentResolver.insert(uri, values));
        //再插入data表 放置数据
        //姓名
        uri = Uri.parse("content://com.android.contacts/data");
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data2", "zdong");
        values.put("data1", "xzdong");
        mContentResolver.insert(uri, values);
        values.clear();
        //电话
        values.put("raw_contact_id", contact_id);
        values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data2", "2");   //手机
        values.put("data1", "87654321");
        Uri insertUri = mContentResolver.insert(uri, values);
        if (insertUri != null) {
            ToastUtil.showMessage("添加成功");
        }
        values.clear();
    }

}
