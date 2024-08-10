package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Category(
    val id: Int,
    val productName: List<ProductTypeId>
)