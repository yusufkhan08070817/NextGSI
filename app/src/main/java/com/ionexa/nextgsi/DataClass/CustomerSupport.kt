package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date


data class CustomerSupport(
    val id: Int,
    val customerId: Int,
    val ticketId: Int,
    val issueDescription: String,
    val status: String,
    val createdAt: String
)
