package com.ionexa.nextgsi.SingleTon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

object Navigation {
    var position by mutableStateOf(1)
    lateinit var navController: NavHostController
}