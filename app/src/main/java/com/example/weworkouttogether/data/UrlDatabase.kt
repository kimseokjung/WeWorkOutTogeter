package com.example.weworkouttogether.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weworkouttogether.App

@Database(entities = [PostUrl::class], version = 2, exportSchema = false)
abstract class UrlDatabase : RoomDatabase() {
    abstract fun postUrlDao(): PostUrlDao

    companion object {
        @Volatile
        private var instance: UrlDatabase? = null

        fun getInstance(): UrlDatabase? {
            if (instance == null) {
                synchronized(UrlDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        App.instance,
                        UrlDatabase::class.java,
                        "posts"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}