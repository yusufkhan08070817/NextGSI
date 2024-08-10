package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable
import java.util.Date


data class Customer(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val profilePic: String,
    val address: String,
    val username: String,
    val password: String,
    val role: String,
    val createdAt: String
)
