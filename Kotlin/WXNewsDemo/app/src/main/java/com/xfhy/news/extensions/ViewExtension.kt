package com.xfhy.news.extensions

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * @author xfhy
 * create at 2017/12/23 8:57
 * description：View的扩展
 */

val View.context: Context
    get() = context

//扩展Context
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

//扩展Fragment
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

//扩展View 方便show SnackBar
fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
