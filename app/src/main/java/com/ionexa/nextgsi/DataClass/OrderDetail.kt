package com.ionexa.nextgsi.DataClass


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class OrderDetail(
    val orderId: String,
    val userId: String,
    val orderDate: String,
    val status: String,
    val shippingAddress: String,
    val items: List<OrderItemDetail>
) : Parcelable

@Parcelize
data class OrderItemDetail(
    val productId: String,
    val quantity: Int,
    val priceAtPurchase: String
) : Parcelable
