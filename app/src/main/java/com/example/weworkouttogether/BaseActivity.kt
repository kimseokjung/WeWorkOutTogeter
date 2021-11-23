package com.example.weworkouttogether

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setUpEvents() //이벤트를 처리하기 위한곳
    abstract fun setValues()

    open fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    open fun savePref(key: String?, value: String?,) {
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        Log.e("TAG", "savePref: $key : $value", )
    }

    open fun getData(key: String?): String? {
        var value = ""
        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        if(sharedPreferences.contains("login")){

        }
        value = sharedPreferences.getString(key, "@#@#@@$@$").toString()
        return value
    }

    open fun deletePref() {
        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}