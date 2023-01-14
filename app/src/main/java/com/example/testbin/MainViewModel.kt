package com.example.testbin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testbin.adapters.CardItemModel

class MainViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<CardItemModel>()
}