package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val profilePic: String,
    val address: String,
    val username: String,
    val password: String,
    val role: String,
    val createdAt: Date
)