package com.ionexa.nextgsi.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun ItemList(imageUrl: List<String>, color: Color = Color.White) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(120.dp)

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(100.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(100.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row {
                        when (imageUrl.size) {
                            0 -> {
                                Card(
                                    Modifier
                                        .width(80.dp)
                                        .height(80.dp),
                                    colors = CardDefaults.cardColors(containerColor = color),
                                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                                ) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(shape = CircleShape)
                                            .padding(2.dp)
                                            .fillMaxSize(1f),
                                        contentScale = ContentScale.Crop

                                    )
                                }
                            }

                            in 1..4 -> {
                                Card(
                                    Modifier
                                        .width(80.dp)
                                        .height(80.dp)
                                ) {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2), // 2 columns for a grid of 4 items
                                        contentPadding = PaddingValues(10.dp)
                                    ) {
                                        items(imageUrl.size) { imageindex ->
                                            CoilImage(imageUrl = imageUrl[imageindex])
                                        }
                                    }
                                }

                            }

                            else -> {Card(
                                Modifier
                                    .width(80.dp)
                                    .height(80.dp),
                                colors = CardDefaults.cardColors(containerColor = color),
                                elevation = CardDefaults.elevatedCardElevation(10.dp)
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(shape = CircleShape)
                                        .padding(2.dp)
                                        .fillMaxSize(1f),
                                    contentScale = ContentScale.Crop

                                )
                            }}


                        }
                    }
                    Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceAround) {
                        Text(text = "orderName")
                        Text(text = "Price")
                    }
                }
            }
        }
    }
}

@Composable
fun CoilImage(modifier: Modifier = Modifier, imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .clip(shape = CircleShape)
            .height(30.dp)
            .width(40.dp)
            .padding(2.dp),
        contentScale = ContentScale.Crop

    )
}


