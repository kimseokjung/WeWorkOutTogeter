package com.example.weworkouttogether.datas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostUrl(
    @PrimaryKey(autoGenerate = true) val idx: Int?,
    @ColumnInfo(name = "post_url") val postUrl: String?,
    @ColumnInfo(name = "post_title") val postTitle: String?,
    @ColumnInfo(name = "post_photo") val postPhoto: String?,

    )