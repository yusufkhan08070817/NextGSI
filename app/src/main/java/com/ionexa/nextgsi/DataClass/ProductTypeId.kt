package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class ProductTypeId(
    val productid: Int,
    val name: String,
    val price: Int,
    val images: List<String>,
    val offer: Int,
    val seller: Seller,
    val rating: Float,
    val address: String,
    val productQuantity: String,
    val types: String,
    val productDetails: String,
    val shopid: Int,
    val extraInfo: ExtraInfo,
    val review: Review,
    val availableStock: Boolean
)