package com.example.weworkouttogether.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo

class ListItemViewModel():ViewModel() {

    // data
    private var _idText = MutableLiveData<String>()
    private var _postTitle = MutableLiveData<String>()
    private val _postUrl = MutableLiveData<String>()
    private val _postPhoto = MutableLiveData<String>()


    //live data
    val idText : LiveData<String> get() = _idText
    val postTitle : LiveData<String> get() = _postTitle
    val postUrl : LiveData<String> get() = _postUrl
    val postPhoto : LiveData<String> get() = _postPhoto

    fun setIdText(value: String) {
        _idText.value = value
    }
    fun setTitleText(value: String) {
        _postTitle.value = value
    }
    fun setUrlText(value: String) {
        _postUrl.value = value
    }
    fun setPhotoText(value: String) {
        _postPhoto.value = value
    }




}