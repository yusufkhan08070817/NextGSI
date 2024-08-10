package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Shop(
    val id: Int,
    val sellerId: Int,
    val name: String,
    val description: String,
    val logo: String,
    val banner: String,
    val ratings: Float,
    val reviews: List<Int>,
    val openingHours: String,
    val returnPolicy: String,
    val shippingPolicy: String
)
