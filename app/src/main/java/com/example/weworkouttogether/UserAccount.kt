package com.example.weworkouttogether

/*
    사용자 계정 정보 모델 클라스
 */


 data class UserAccount(
     val idToken: String, //고유 토큰 정보
     var emailId:String,
     var pwd:String
 )