package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable



data class SellerListedProduct(
    val id: Int,
    val sellerId: Int,
    val name: String,
    val description: String,
    val images: List<String>,
    val category: String,
    val price: Float,
    val stock: Int,
    val sku: String,
    val variants: String,
    val discount: Float,
    val shippingInfo: String,
    val reviews: List<Int>,
    val rating: Float
)

