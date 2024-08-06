package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CustomerSupport(
    val id: Int,
    val customerId: Int,
    val ticketId: Int,
    val issueDescription: String,
    val status: String,
    val createdAt: Date
)
