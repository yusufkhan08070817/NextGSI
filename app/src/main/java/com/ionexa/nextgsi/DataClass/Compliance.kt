package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Compliance(
    val id: Int,
    val sellerId: Int,
    val documents: String,
    val verificationStatus: String
)