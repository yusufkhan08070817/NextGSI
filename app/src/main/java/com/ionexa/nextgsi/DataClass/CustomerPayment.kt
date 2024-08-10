package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date

data class CustomerPayment(
    val id: Int,
    val customerId: Int,
    val method: String,
    val cardNumber: String,
    val cardName: String,
    val expiryDate: String,
    val cvv: Int,
    val billingAddress: String,
    val createdAt: String
)