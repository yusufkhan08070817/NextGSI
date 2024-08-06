package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable

@Serializable
data class Support(
    val id: Int,
    val sellerId: Int,
    val tickets: List<Int>,
    val communicationHistory: String
)