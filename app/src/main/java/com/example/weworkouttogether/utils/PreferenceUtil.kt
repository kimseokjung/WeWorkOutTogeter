package com.example.weworkouttogether.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class PreferenceUtil(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE)

    fun getData(key:String): String {
        return pref.getString(key,"!@#$!@##").toString()
    }

    fun setPref(key: String, value: String){
        pref.edit().putString(key, value).apply()
    }

    fun deletePref() {
        pref.edit().clear().apply()
    }
}