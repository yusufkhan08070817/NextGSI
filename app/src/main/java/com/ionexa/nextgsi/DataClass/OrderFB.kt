package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date


data class OrderFB(
    val id: Int,
    val sellerId: Int,
    val orderDate: String,
    val customerId: Int,
    val productId: List<Int>,
    val quantity: Int,
    val totalPrice: Float,
    val status: String,
    val shippingAddress: String,
    val trackingNumber: String,
    val paymentStatus: String
)