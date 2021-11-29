package com.example.weworkouttogether.data

import java.io.Serializable

class Blog(
    val title: String,
    val photoUrl: String,
    val postUrl: String
) : Serializable {
}