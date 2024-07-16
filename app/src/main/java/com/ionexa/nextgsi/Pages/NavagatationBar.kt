package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Navagatation(modifier: Modifier = Modifier) {
    var isAdmin by remember { mutableStateOf(false) }
    var positation by remember {
        mutableStateOf(1)
    }
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(0.dp)
            .background(color = Color(0xFF0386D0))
    ) {
        Row(modifier = modifier
            .padding(15.dp)
            .fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceAround) {
            Card(colors = CardDefaults.cardColors(containerColor =if (positation==1) Color(0xFF00649C)  else Color(0xFF0386D0) )) {
                IconButton(onClick = {positation=1}) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = Color.White
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = if (positation==2) Color(0xFF00649C)  else Color(0xFF0386D0))) {
                IconButton(onClick = {positation=2}) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "home",
                        tint = Color.White
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = if (positation==3) Color(0xFF00649C)  else Color(0xFF0386D0))) {
                IconButton(onClick = {positation=3}) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "home",
                        tint = Color.White
                    )
                }
            }
            if(isAdmin)
                Card(colors = CardDefaults.cardColors(containerColor = if (positation==4) Color(0xFF00649C)  else Color(0xFF0386D0))) {
                    IconButton(onClick = {positation=4}) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "home",
                            tint = Color.White
                        )
                    }
                }
        }
    }
}