package com.ionexa.nextgsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

data class Order(val imageUrl: String, val itemName: String, val itemDetails: String, val totalCost: String)

class OrderHistoryActivity : ComponentActivity() {
    private val orderList = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OrderHistoryScreen(orderList)
            }
        }
        // Add some local data for testing
        addLocalData()
    }

    private fun addLocalData() {
        val orders = listOf(
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 1", "Details of Item 1", "₹100"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 2", "Details of Item 2", "₹200"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 3", "Details of Item 3", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 4", "Details of Item 4", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 5", "Details of Item 5", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 6", "Details of Item 6", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 7", "Details of Item 7", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 8", "Details of Item 8", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 9", "Details of Item 9", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 10", "Details of Item 10", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 11", "Details of Item 11", "₹400")
        )
        orderList.addAll(orders)
    }
}@Composable
fun OrderHistoryScreen(orderList: List<Order>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // RecyclerView
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(orderList) { order ->
                OrderItem(order)
            }
        }

        // Spacer to push the bill summary upward
        Spacer(modifier = Modifier.height(16.dp))

        // Bill summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                text = "Bill Summary",
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp).padding(start = 16.dp)
            )
            val totalSum = orderList.sumOf {
                it.totalCost.replace("₹", "").trim().toInt()
            }
            Text(
                text = "₹$totalSum",
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp).padding(end = 16.dp)
            )
        }
    }
}



@Composable
fun OrderItem(order: Order) {
    // Composable for displaying each order item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(order.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = order.itemName, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(text = order.itemDetails)
        }
        Text(
            text = order.totalCost,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


