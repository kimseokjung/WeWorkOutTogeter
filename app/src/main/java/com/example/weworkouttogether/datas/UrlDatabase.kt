package com.example.weworkouttogether.datas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostUrl::class], version = 2, exportSchema = false)
abstract class UrlDatabase : RoomDatabase() {
    abstract fun postUrlDao(): PostUrlDao

    companion object {
        private var instance: UrlDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UrlDatabase? {
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                    UrlDatabase::class.java,
                    "posts")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}