package com.ionexa.nextgsi.MVVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeMVVM :ViewModel() {
    var serchtext by mutableStateOf("")
        private set
    fun serserchtext(value:String)
    {
        serchtext=value
    }
    var filter by mutableStateOf(false)
        private set
    fun setfilter(value:Boolean)
    {
        filter=value
    }
}