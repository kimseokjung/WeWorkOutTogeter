package com.example.weworkouttogeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        TODO("이벤트 처리 모음")
    }

    override fun setValues() {
        TODO("데이터 설정 표시")
    }

}