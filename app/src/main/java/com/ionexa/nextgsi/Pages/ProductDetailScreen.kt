package com.ionexa.nextgsi.Pages

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ionexa.nextgsi.Components.AutoSlidingCarousel
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.ui.theme.DarkOrchid

data class Review(
    val name: String,
    val reviewText: String,
    val rating: Int
)

val reviews = listOf(
    Review("John Doe", "Great product!", 5),
    Review("Jane Smith", "Good value for the money.", 4),
    Review("Sam Wilson", "Average experience.", 3),
    Review("Lisa Brown", "Not satisfied with the quality.", 2)
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailScreen() {

    val context = LocalContext.current
    val productDetail = "dummy"
    var quantity by remember { mutableStateOf(1) }
    val errorMessage = "Cant load page"
    val images = listOf(
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
    )

    val scrollState = rememberScrollState()

    if (false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (productDetail != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card {
                        AutoSlidingCarousel(
                            itemsCount = images.size,
                            itemContent = { index ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(images[index])
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 45.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .background(color = Color.White, shape = CircleShape)
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.left_arrow),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(6.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .width(70.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(3.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "4.8",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "WestSide Jacket",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 22.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "$200",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }

                            var isLiked by remember { mutableStateOf(false) }
                            IconButton(onClick = { isLiked = !isLiked }) {
                                Image(
                                    painter = painterResource(id = if (isLiked) R.drawable.heart else R.drawable.baseline_favorite_border_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(20.dp))
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                                .background(Color(0xFFE8ECEE))
                        )
                        Text(
                            text = "Description :",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        )
                        Text(
                            text = "THIS is a dummy text for the description of the product. THIS is a dummy text for the description of the product. THIS is a dummy text for the description of the product. THIS is a dummy text for the description of the product ",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                                .background(Color(0xFFE8ECEE))
                        )
                        var showDetails by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        ) {
                            Button(onClick = { showDetails = !showDetails },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Text(
                                    text = if (showDetails) "Hide Details" else "See more Details",
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Icon(
                                    painter = painterResource(id = if (showDetails) R.drawable.baseline_arrow_drop_up_24 else R.drawable.baseline_arrow_drop_down_24),
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }
                        }

                        if (showDetails) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Manufacturer: XYZ Corp\nWarranty: 1 Year\nMaterial: 100% Cotton",
                                fontSize = 16.sp,
                                color = Color.DarkGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp, 0.dp, 15.dp, 15.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(Color(0xFFE8ECEE))
                    )

                    Text(
                        text = "Colour :",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp, 15.dp, 15.dp, 0.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            )
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            items(2) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Green,
                                            shape = CircleShape
                                        )
                                        .padding(5.dp)
                                        .background(color = Color.Cyan, shape = CircleShape)
                                        .clip(CircleShape)
                                        .clickable {

                                        }
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .border(width = 1.dp,color = Color.Black, shape = CircleShape)

                        ) {
                            IconButton(
                                onClick = {quantity=quantity-1},
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.minus_small_4338830),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "${quantity}",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .width(25.dp)
                                    .wrapContentHeight()
                            )
                            IconButton(
                                onClick = {
                                    quantity=quantity+1
                                },
                                modifier = Modifier
                                    .background(color = Color.White, shape = CircleShape)
                                    .clip(CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.plus_small_4338829),
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(Color(0xFFE8ECEE))
                    )
                    //SIMILAR PRODUCTS
                    Text(
                        text = "Similar Products :",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp, 15.dp, 15.dp, 0.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            )
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            items(7) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(3.dp,3.dp,3.dp,3.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp)
                                        )
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(images[0])
                                            .build(),
                                        contentDescription = "Product image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(84.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .padding(12.dp,12.dp,12.dp,12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Product Title", // Replace with your product title
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(12.dp,12.dp,12.dp,12.dp)
                                    )
                                    Text(
                                        text = "$19.99", // Replace with your product price
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .padding(12.dp,6.dp,6.dp,12.dp)
                                    )
                                }
                            }
                        }
                    }

                    //CUSTOMER REVIEWS
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(Color(0xFFE8ECEE))
                    )
                    Text(
                        text = "Customer Reviews :",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )
                }
            }

            items(reviews) { review ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(Color.White, shape = RoundedCornerShape(0.dp))
                        .padding(5.dp,2.dp,5.dp,2.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomStart = 5.dp, bottomEnd = 5.dp)
                        )
                ) {
                    Text(
                        text = review.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(5.dp,5.dp,5.dp,0.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = review.reviewText,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(5.dp,2.dp,5.dp,0.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        repeat(review.rating) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.Green
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(DarkOrchid),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp,15.dp,30.dp,15.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        onClick = {
                            Toast.makeText(
                                context,
                                "Successfully added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    ) {
                        Text(text = "Add to Cart", fontSize = 16.sp)
                    }
                }
            }
        }

    } else {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
