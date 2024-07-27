package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ionexa.nextgsi.DataClass.Order

@Composable
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
