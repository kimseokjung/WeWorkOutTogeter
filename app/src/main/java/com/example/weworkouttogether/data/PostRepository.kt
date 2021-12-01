package com.example.weworkouttogether.data

import androidx.lifecycle.LiveData

class PostRepository() {

    private val postUrlDao: PostUrlDao by lazy {
        val db = UrlDatabase.getInstance() as UrlDatabase
        db.postUrlDao()
    }
    private val posts: LiveData<MutableList<PostSingleItem>> by lazy {
        postUrlDao.getAll()
    }
    fun getAll() : LiveData<MutableList<PostSingleItem>>{
        return posts
    }

}