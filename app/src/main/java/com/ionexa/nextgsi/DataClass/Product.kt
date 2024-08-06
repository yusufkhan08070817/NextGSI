package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val category: Category
)