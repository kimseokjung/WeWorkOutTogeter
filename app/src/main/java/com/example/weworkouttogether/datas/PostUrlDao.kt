package com.example.weworkouttogether.datas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostUrlDao {
    @Query("SELECT * FROM posts")
    fun getAll(): MutableList<PostUrl>

    @Insert
    fun insertUrl(vararg postUrl:PostUrl)

    @Delete
    fun delete(postUrl: PostUrl)

    @Query("DELETE FROM posts")
    fun clearAll()
}