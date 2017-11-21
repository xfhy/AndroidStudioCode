package com.xfhy.roundimageview

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            riv_test.setImageDrawable(getDrawable(R.drawable.test))
            Glide.with(this).load("http://pic3.zhimg.com/f486d773bd56e24c2ffca4e31ba82aea.jpg")
                    .into(riv_test)
        }
    }
}
