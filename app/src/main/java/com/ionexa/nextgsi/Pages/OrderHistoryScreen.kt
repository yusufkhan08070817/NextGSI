package com.ionexa.nextgsi.Pages

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.ionexa.nextgsi.DataClass.Order

import com.ionexa.nextgsi.DataClass.OrderDetail
import com.ionexa.nextgsi.DataClass.OrderItemDetail

@Composable
fun OrderHistoryScreen(orderList: List<Order>) {
    var detaildialog by remember {
        mutableStateOf<OrderDetail>(OrderDetail("","","","","",listOf()))
    }
    var itemclick by remember {
        mutableStateOf(false)
    }
   Box(  modifier = Modifier.fillMaxSize(), )
   {
       Column(
           modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.SpaceBetween
       ) {
           Log.e("OrderHistoryScreen: ","----------------------------------------------")
           // RecyclerView
           LazyColumn(
               modifier = Modifier.weight(1f),
               contentPadding = PaddingValues(8.dp)
           ) {
               items(orderList) { order ->
                   OrderItem(order){
                       itemclick=true;
                       detaildialog= OrderDetail(
                           orderId = "12345",
                           userId = "user123",
                           orderDate = "2024-08-02",
                           status = "Delivered",
                           shippingAddress = "123 Main St, City, Country",
                           items = listOf(
                               OrderItemDetail("prod1", 2, "₹100"),
                               OrderItemDetail("prod2", 1, "₹200")
                           )
                       )
                   }
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
                   modifier = Modifier
                       .padding(bottom = 16.dp)
                       .padding(start = 16.dp)
               )
               val totalSum = orderList.sumOf {
                   it.totalCost.replace("₹", "").trim().toInt()
               }
               Text(
                   text = "₹$totalSum",
                   style = TextStyle(fontWeight = FontWeight.Bold),
                   modifier = Modifier
                       .padding(bottom = 16.dp)
                       .padding(end = 16.dp)
               )
           }
       }
      AnimatedVisibility(visible = itemclick) {
          Dialog(onDismissRequest = { itemclick=false }, properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = true, dismissOnClickOutside = true)) {
              OrderDetailScreen(detaildialog)
          }
      }
   }
}



@Composable
fun OrderItem(order: Order,onclick:()->Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onclick()
            },
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





