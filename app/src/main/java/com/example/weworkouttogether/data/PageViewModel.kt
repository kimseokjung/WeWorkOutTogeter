package com.example.weworkouttogether.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room

class PageViewModel(private var app: Application) : ViewModel() {
    private val dao = Room.databaseBuilder(app, UrlDatabase::class.java, "posts")
        .build().postUrlDao()

    val data = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
        ), pagingSourceFactory = {PostPagingSource(dao)}
    ).flow

}