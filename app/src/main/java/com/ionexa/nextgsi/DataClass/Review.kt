package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Review(
    val userid: Int,
    val sellerId: Int,
    val username: String,
    val profilePic: String,
    val comment: String
)