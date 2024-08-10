package com.ionexa.nextgsi.DataClass

import kotlinx.serialization.Serializable


data class Analytics(
    val id: Int,
    val sellerId: Int,
    val salesData: String,
    val revenueReports: String,
    val inventoryReports: String,
    val customerInsights: String
)
