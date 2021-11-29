package com.example.weworkouttogether.utils

import android.content.Context
import android.util.Log
import com.example.weworkouttogether.data.UrlDatabase

class RoomDataUtil() {
    private var db: UrlDatabase = UrlDatabase.getInstance() as UrlDatabase

    fun clearAll(){
        // 로그아웃시 data table을 비운다
        try {
            db.postUrlDao().clearAll()
            Log.d("TAG", "onDestroy: data clear")
        } catch (e: Exception) {
            Log.e("TAG", "onDestroy: ${e.toString()}")
        }
    }
}