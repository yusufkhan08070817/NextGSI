package com.ionexa.nextgsi.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ionexa.nextgsi.DataClass.CartItemInfo
import com.ionexa.nextgsi.ui.theme.IndigoHeading

@Composable
fun CartItem(
    modifier: Modifier = Modifier, data:CartItemInfo
) {
var quantity by remember {
    mutableStateOf(data.quantity)
}
    var price by remember {
        mutableStateOf(data.price)
    }
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .height(100.dp)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier
                .fillMaxWidth(1f)
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = data.url,
                contentDescription = "prodict image",
                modifier
                    .height(40.dp)
                    .width(40.dp)
                    .shadow(10.dp, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(text = data.productname)
            Row (horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){

                Card(
                    modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { quantity = quantity + 1 }, colors = CardDefaults.cardColors(containerColor = IndigoHeading), elevation = CardDefaults.elevatedCardElevation(10.dp)) {
                   Row (modifier.fillMaxSize(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                       Text(text = "+", color = Color.White)
                   }
                }
                Text(text = "${quantity}", modifier = Modifier.width(40.dp), textAlign = TextAlign.Center)
                Card(
                    modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { quantity = quantity - 1 }, colors = CardDefaults.cardColors(containerColor = IndigoHeading), elevation = CardDefaults.elevatedCardElevation(10.dp)) {
                    Row (
                        modifier
                            .fillMaxSize(1f)
                            .padding(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text = "-", color = Color.White)
                    }
                }

            }
            Text(text = "${price*quantity}")
        }
    }
}

@Composable
fun BillComponent(modifier: Modifier = Modifier,name:String,price:Float,quantity:Int) {
    Row ( modifier = Modifier.fillMaxWidth(1f).padding(10.dp),horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        Text(text =name ,color=Color.White)
        Text(text = quantity.toString(),color=Color.White)
        Text(text = price.toString(),color=Color.White )
    }

}