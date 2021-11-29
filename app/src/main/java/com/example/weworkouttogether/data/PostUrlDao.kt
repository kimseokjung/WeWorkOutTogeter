package com.example.weworkouttogether.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostUrlDao {
    @Query("SELECT * FROM posts")
    fun getAll(): LiveData<MutableList<PostUrl>>

    @Insert
    fun insertUrl(vararg postUrl:PostUrl)

    @Delete
    fun delete(postUrl: PostUrl)

    @Query("DELETE FROM posts")
    fun clearAll()

    @Query("select * from posts idx LIMIT :loadSize OFFSET :index * :loadSize")
    fun getPage(index : Int, loadSize : Int) : MutableList<PostUrl>

}