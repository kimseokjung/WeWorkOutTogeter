package com.example.weworkouttogether

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weworkouttogether.data.UrlDatabase

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setUpEvents() //이벤트를 처리하기 위한곳
    abstract fun setValues()

    open fun clearAll(context: Context){
        // 로그아웃시 data table을 비운다
        var db = UrlDatabase.getInstance() as UrlDatabase
        try {
            db.postUrlDao().clearAll()
            Log.d("TAG", "onDestroy: data clear")
        } catch (e: Exception) {
            Log.e("TAG", "onDestroy: ${e.toString()}")
        }
    }

    open fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}