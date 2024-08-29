package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class ProductTypeId(
    var productid: String = "",
    var name: String = "",
    var price: String = "",
    var images: List<String> = listOf(),
    var offer: String = "",
    var seller: String = "",
    var rating: String = "",
    var address: String = "",
    var productQuantity: String = "",
    var types: String = "",
    var productDetails: String = "",
    var shopid: String = "",
    var extraInfo:ExtraInfo= ExtraInfo(""),
    var review: String = "",
    var availableStock: String = ""
)
