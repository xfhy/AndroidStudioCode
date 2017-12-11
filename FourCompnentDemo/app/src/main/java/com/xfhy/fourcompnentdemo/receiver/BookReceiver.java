package com.xfhy.fourcompnentdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xfhy.fourcompnentdemo.service.BookService;
import com.xfhy.fourcompnentdemo.util.ToastUtil;

/**
 * @author feiyang
 *         time create at 2017/11/28 13:35
 *         description
 */
public class BookReceiver extends BroadcastReceiver {

    private static final String TAG = "BookReceiver";
    public final static String BOOK_RECEIVER_ACTION = "com.xfhy.book.receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BOOK_RECEIVER_ACTION.equals(intent.getAction())) {
            Log.e(TAG, "BookReceiver收到消息");
            ToastUtil.showMessage("BookReceiver收到消息");
            Intent bookIntent = new Intent(context, BookService.class);
            context.startService(bookIntent);
        }
    }
}
