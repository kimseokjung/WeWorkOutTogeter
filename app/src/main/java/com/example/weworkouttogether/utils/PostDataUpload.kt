package com.example.weworkouttogether.utils

import android.sax.Element
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class PostDataUpload() {

    fun dataUpload(url: String, category: String) {
        try {
            //페이지
            var pageArr = arrayListOf<org.jsoup.nodes.Element>()
            var page = 1
            var getPage = Jsoup.connect(url + page).get()
            var getLastPage = getPage.select("div.blog2_paginate a href")
            for (i in getLastPage) {
                pageArr.add(i)
            }
            var maxPage = pageArr.size + 1

            var doc = Jsoup.connect(url + page).get()
            var elements = doc.select("div.post_album_view_s966 div ul li a")

            var db = Firebase.database.getReference("workout")

            while (page > maxPage) {

                for (e in elements) {
                    var url = e.absUrl("href")
                    var title = e.select("div.area_text strong").text()
                    var src = e.select("div.area_thumb img").first()
                    var photoUrl = src.absUrl("src")

                    Log.d("TAG", url)
                    Log.d("TAG", title)
                    Log.d("TAG", photoUrl)
                    var insertData = hashMapOf(
                        "url" to url,
                        "title" to title,
                        "src" to src,
                        "photoUrl" to photoUrl
                    )
                    try {
                        db.child("post").child(category).child(title)
                            .setValue(insertData)
                            .addOnSuccessListener {
                                Log.d(
                                    "firebase",
                                    "$category Data add Success!!"
                                )
                            }
                            .addOnFailureListener { Log.d("firebase", "$category Data add Fail!!") }

                    } catch (e: Exception) {
                        Log.e("TAG", e.toString())
                    }
                }
                page++
            }

        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

}