package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Seller(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val profilePic: String,
    val address: String,
    val businessName: String,
    val businessRegistrationNumber: String,
    val taxInfo: String,
    val username: String,
    val password: String,
    val role: String,
    val gstNumber: String,
    val aadharNumber: String
)
