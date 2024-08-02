package com.ionexa.nextgsi.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.ionexa.nextgsi.DataClass.OrderDetail

class OrderDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderDetail = intent.getParcelableExtra<OrderDetail>("orderDetail")
        setContent {
            MaterialTheme {
                OrderDetailScreen(orderDetail)
            }
        }
    }
}

