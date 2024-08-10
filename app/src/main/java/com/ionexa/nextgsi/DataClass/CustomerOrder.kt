package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date


data class CustomerOrder(
    val id: Int,
    val customerId: Int,
    val orderDate: String,
    val productId: List<Int>,
    val quantity: Int,
    val totalPrice: Float,
    val status: String,
    val shippingAddress: String,
    val sellerID: Int,
    val trackingNumber: String,
    val paymentStatus: String
)
