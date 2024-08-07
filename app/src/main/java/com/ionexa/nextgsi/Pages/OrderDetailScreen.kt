package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ionexa.nextgsi.DataClass.OrderDetail
import com.ionexa.nextgsi.DataClass.OrderItemDetail

@Composable
fun OrderDetailScreen(orderDetail: OrderDetail?) {
    orderDetail?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Order ID: ${it.orderId}",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                    Text(text = "User ID: ${it.userId}", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Order Date: ${it.orderDate}", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Status: ${it.status}", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Shipping Address: ${it.shippingAddress}",
                        style = TextStyle(fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Items:",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    LazyColumn {
                        items(it.items) { item ->
                            OrderItemDetailView(item)
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemDetailView(item: OrderItemDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Product ID: ${item.productId}", style = TextStyle(fontSize = 16.sp))
            Text(text = "Quantity: ${item.quantity}", style = TextStyle(fontSize = 16.sp))
            Text(text = "Price: ${item.priceAtPurchase}", style = TextStyle(fontSize = 16.sp))
        }
    }
}
