package com.example.weworkouttogether

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents() //이벤트를 처리하기 위한곳
    abstract fun setValues()

}