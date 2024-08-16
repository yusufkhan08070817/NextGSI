package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class ProductTypeId(
    val productid: String = "",
    val name: String = "",
    val price: String = "",
    val images: List<String> = listOf(),
    val offer: String = "",
    val seller: String = "",
    val rating: String = "",
    val address: String = "",
    val productQuantity: String = "",
    val types: String = "",
    val productDetails: String = "",
    val shopid: String = "",
    val extraInfo:ExtraInfo= ExtraInfo(""),
    val review: String = "",
    val availableStock: String = ""
)
