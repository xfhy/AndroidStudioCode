package com.xfhy.basicdraw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xfhy.basicdraw.entity.AbilityBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = AbilityBean()
        data.ad = 75
        data.ap = 45
        data.assist = 56
        data.defense = 34
        data.kill = 56
        data.money = 78
        data.survival = 70
        av_test.mData = data
    }
}
