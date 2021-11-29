package com.example.weworkouttogether.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.weworkouttogether.data.UrlDatabase

class ForcedTerminationService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e("TAG", "onTaskRemoved: 강제 종료 $rootIntent")

        Toast.makeText(this, "onTaskRemoved", Toast.LENGTH_SHORT).show()
        stopSelf()
    }
}