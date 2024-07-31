package com.ionexa.nextgsi.MVVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var isFocused by  mutableStateOf(false)
        private set
    fun updateisFocuse(value:Boolean)
    {
        isFocused=value
    }
    // State variables
    var lowPrice by mutableStateOf(false)
        private set
    var midPrice by mutableStateOf(false)
        private set
    var highPrice by mutableStateOf(false)
        private set

    var nearMe by mutableStateOf(false)
        private set
    var sameState by mutableStateOf(false)
        private set

    var available by mutableStateOf(false)
        private set
    var unavailable by mutableStateOf(false)
        private set

    var freeDelivery by mutableStateOf(false)
        private set
    var paid by mutableStateOf(false)
        private set

    // Update functions
    fun updateLowPrice(value: Boolean) {
        lowPrice = value
    }

    fun updateMidPrice(value: Boolean) {
        midPrice = value
    }

    fun updateHighPrice(value: Boolean) {
        highPrice = value
    }

    fun updateNearMe(value: Boolean) {
        nearMe = value
    }

    fun updateSameState(value: Boolean) {
        sameState = value
    }

    fun updateAvailable(value: Boolean) {
        available = value
    }

    fun updateUnavailable(value: Boolean) {
        unavailable = value
    }

    fun updateFreeDelivery(value: Boolean) {
        freeDelivery = value
    }

    fun updatePaid(value: Boolean) {
        paid = value
    }
}