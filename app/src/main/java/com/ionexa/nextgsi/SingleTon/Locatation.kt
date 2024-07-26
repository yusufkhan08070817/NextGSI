package com.ionexa.nextgsi.SingleTon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

object Locatation {
    var latitude by mutableStateOf(0.0)
    var longitude by mutableStateOf(0.0)
}