package com.ionexa.nextgsi.DataClass

data class CartInfo(
    var image: String = "",
    var name: String = "",
    var price: String = "",
    var quantity: String = "",
    var sellerId: String,
    var customerId: String = "",
    var shippingAddress: String = "",
var productindex:Int?=null
)
