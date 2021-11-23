package com.example.weworkouttogether.datas

import java.io.Serializable

class Store(
    val writeNum: Int,
    val name: String,
    val tel: String,
    val photoUrl: Int
) : Serializable {
}