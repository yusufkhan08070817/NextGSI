package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun coupons(modifier: Modifier = Modifier) {

   Column(modifier = Modifier.fillMaxSize(1f)) {
      Card(modifier = Modifier
          .fillMaxWidth(1f)
          .fillMaxHeight(0.1f)) {
       Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
           Text(text = "Coupon")
       }
      }

       LazyVerticalGrid(
           columns = GridCells.Adaptive(minSize = 100.dp), // Defines adaptive columns with a minimum size
           modifier = Modifier
               .fillMaxWidth(1f)
               .fillMaxHeight(0.7f),
           contentPadding = PaddingValues(8.dp)
       ) {
           items(10) { index ->
               CoupenCard("https://cdn.britannica.com/94/151894-050-F72A5317/Brown-eggs.jpg","KFC")
           }
       }

   }
}

@Composable
fun CoupenCard(image:String,name:String) {
    Card(modifier = Modifier
        .size(120.dp)
        .padding(10.dp)) {
        Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
          AsyncImage(model = image, contentDescription = "", modifier = Modifier.size(50.dp))
            Text(text = "$name \n 20%off")
        }
    }
}