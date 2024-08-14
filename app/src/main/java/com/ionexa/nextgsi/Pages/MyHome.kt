package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ionexa.nextgsi.SingleTon.common

data class tempdata(val image: String, val title: String)

@Composable
fun MainHomeList(modifier: Modifier = Modifier, data: MutableList<MutableList<tempdata>>,mycomposable: @Composable () -> Unit) {
    LazyColumn(
        modifier = modifier,
       
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            mycomposable()
        }

        itemsIndexed(data) { index, itemList ->
            // Use index and itemList
            Text(
                text = "Categories",
                fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp)
            )
            HorizontalScrollRow(data = itemList, index = index)
            Spacer(modifier = Modifier
                .fillMaxWidth(1f)
                .height(90.dp))
            if (index%3==0)
            {
                AsyncImage(model = "https://graphicsfamily.com/wp-content/uploads/edd/2022/11/Professional-Advertising-Poster-Design-for-Tea-Product-1180x664.jpg", contentDescription ="" ,
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp), contentScale = ContentScale.Crop )
            }
        }
    }
}

@Composable
fun HorizontalScrollRow(data: MutableList<tempdata>, index: Int) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth() // Fill the available width
            .padding(vertical = 8.dp), // Add vertical padding if needed
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
    ) {
        itemsIndexed(data) { itemIndex, item ->
            // Use itemIndex and item
           Row {
               Spacer(modifier = Modifier.width(10.dp))
               SubItemSquarBox(
                   modifier = Modifier
                       .height(200.dp) // Height of each item
                       .width(200.dp), // Width of each item
                   image = item.image,
                   title = item.title
               )
               Spacer(modifier = Modifier.width(10.dp))
           }
        }
    }
}

@Composable
fun SubItemSquarBox(modifier: Modifier = Modifier, image: String, title: String) {
    Box(modifier = modifier) {
        Card(modifier = Modifier.fillMaxSize()) {
            AsyncImage(model = image, contentDescription = "", contentScale = ContentScale.Crop)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
             Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically){
               Spacer(modifier = Modifier.width(10.dp))
                 IconButton(onClick = { /*TODO*/ }) {
                     Box(modifier= Modifier
                         .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                         .background(Color.White, shape = RoundedCornerShape(10.dp))
                         .padding(10.dp)) {
                         Icon(imageVector = Icons.Default.ShoppingCart, contentDescription ="" )
                     }
                 }
                Column {
                    Text(
                        text = title,
                        color = Color.White,
                        textAlign = TextAlign.Center, fontSize = 15.sp, fontWeight = FontWeight.Bold ,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Price 100rs",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
             }
            }
        }
    }
}
