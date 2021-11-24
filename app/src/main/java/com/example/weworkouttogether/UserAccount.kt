package com.example.weworkouttogether

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/*
    사용자 계정 정보 모델 클라스
 */

 @IgnoreExtraProperties
 data class UserAccount(
     val idToken: String, //고유 토큰 정보
     var email:String,
     var pwd:String,
     var name:String,
     var phone:String,
     var gender:String,
     var admin:String


 ){
     @Exclude
     fun UserAccount(){}
 }