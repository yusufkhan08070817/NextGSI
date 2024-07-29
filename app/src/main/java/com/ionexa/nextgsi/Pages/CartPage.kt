package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ionexa.nextgsi.Components.BillComponent
import com.ionexa.nextgsi.Components.CartItem
import com.ionexa.nextgsi.DataClass.CartItemInfo
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.ui.theme.BalckeshPurple
import com.ionexa.nextgsi.ui.theme.DarkOrchidwebcolor
import com.ionexa.nextgsi.ui.theme.IndigoHeading

@Composable
fun CartPage(modifier: Modifier = Modifier) {
    val listofitem = itemdata()
    var list by remember { mutableStateOf(listofitem) }
    var totalprice by remember {
        mutableStateOf(0f)
    }
    list.map {it->
        totalprice=totalprice+it.price
    }
Row(modifier = Modifier
    .offset(20.dp, 40.dp)
    .fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceAround) {

    Card(modifier = Modifier, colors = CardDefaults.cardColors(Color.White)) {
        Text(text = "Cart Page", color = IndigoHeading,modifier = Modifier.padding(10.dp))
    }

    Card(modifier = Modifier.clickable { Navigation.navController.navigate(NaveLabels.CartHistory) }) {
        Text(text = "Order History", color = IndigoHeading, modifier = Modifier.padding(10.dp))
    }
}
    Column(
        modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)) {

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(top = 40.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            items(list){data->
                CartItem(data=data)
            }
        }

        Card(modifier= Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.6f)
            .padding(top = 40.dp, start = 10.dp, end = 10.dp), colors = CardDefaults.cardColors(BalckeshPurple)) {
            Column(modifier.fillMaxSize(1f)) {
                LazyColumn {
                    items(list){ data->
                        BillComponent(name = data.productname, price = data.price, quantity = data.quantity)

                    }
                }

            }
        }
        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 40.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Pay")
            }
            Text(text = "total : ${totalprice}")
        }

    }
}

fun itemdata(): List<CartItemInfo> {
    val image = "https://cdn.britannica.com/94/151894-050-F72A5317/Brown-eggs.jpg"
    val data = mutableListOf<CartItemInfo>()
    data.add(CartItemInfo(url = image, productname = "Egg", price = 150f, quantity = 12))
    data.add(CartItemInfo(url = image, productname = "Eg", price = 104f, quantity = 12))
    data.add(CartItemInfo(url = image, productname = "Esdgg", price = 1500f, quantity = 120))
    data.add(CartItemInfo(url = image, productname = "Egge", price = 106f, quantity = 12))
    data.add(CartItemInfo(url = image, productname = "Eggwe", price = 170f, quantity = 14))
    data.add(CartItemInfo(url = image, productname = "Egg54", price = 1030f, quantity = 120))
    data.add(CartItemInfo(url = image, productname = "Egg6t", price = 107f, quantity = 13))

    return data
}
