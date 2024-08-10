package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Payment(
    val id: Int,
    val sellerId: Int,
    val method: String,
    val bankAccountDetails: String,
    val transactionHistory: List<Int>
)
