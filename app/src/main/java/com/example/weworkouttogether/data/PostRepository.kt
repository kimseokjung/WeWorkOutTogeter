package com.example.weworkouttogether.data

import androidx.lifecycle.LiveData
import com.example.weworkouttogether.App

class PostRepository() {

    private val postUrlDao: PostUrlDao by lazy {
        val db = UrlDatabase.getInstance() as UrlDatabase
        db.postUrlDao()
    }
    private val posts: LiveData<MutableList<PostUrl>> by lazy {
        postUrlDao.getAll()
    }
    fun getAll() : LiveData<MutableList<PostUrl>>{
        return posts
    }

}