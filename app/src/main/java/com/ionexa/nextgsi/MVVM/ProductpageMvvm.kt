package com.ionexa.nextgsi.MVVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ionexa.nextgsi.DataClass.ProductTypeId

class ProductpageMvvm:ViewModel() {
    var product by mutableStateOf(ProductTypeId())
        private set
    var intemindex by mutableStateOf<Int?>(null)
        private set
    fun updateproduct(value:ProductTypeId)
    {
        product=value

    }
    fun updateindex(value:Int)
    {
        intemindex=value
    }



}