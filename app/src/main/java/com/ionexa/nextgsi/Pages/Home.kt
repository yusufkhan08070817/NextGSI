package com.ionexa.nextgsi.Pages

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.Components.FilterDialog
import com.ionexa.nextgsi.Components.ImageCarouselCard
import com.ionexa.nextgsi.Components.IteamSearch
import com.ionexa.nextgsi.Components.LocationScreen
import com.ionexa.nextgsi.Components.Serchbar
import com.ionexa.nextgsi.DataClass.Search_dataList
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.SingleTon.Locatation

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeMVVM,
    locationProvider: LocationProvider,
    mapViewModel: MapeKCMVVM,
) {
    // Define filter states
    var priceRange by remember { mutableStateOf(0..100) }
    var chosenDate by remember { mutableStateOf("") }
    var available by remember { mutableStateOf(false) }
    var isFreeDelivery by remember { mutableStateOf(false) }
    var filterLocation by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Location Screen
    LocationScreen(Mapekcmvm = mapViewModel, locationProvider = locationProvider)

    val images = listOf(
        "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
    )

    val categories = listOf(
        "Category 1" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 2" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 3" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 4" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 5" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 6" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 7" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "Category 8" to "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
    )

    // Filter items based on the selected filters
    val filteredItems = filterItems(
        items = lundmaderchod(),
        priceRange = priceRange,
        locationFilter = filterLocation,
        available = available,
        freeDelivery = isFreeDelivery
    )


    Column(modifier.fillMaxSize()
    ) {

        val customPurple200 = Color(0xFFBB86FC)
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                clip = true
            )
            .background(customPurple200, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp, topStart = 0.dp, topEnd = 0.dp))
        ) {


            Text(
                text = "Delivering to:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                        .padding(start = 16.dp, top = 45.dp, end = 16.dp, bottom = 0.dp)
            )
            Text(
                text = "B/78, Kalanagar Society, New Delhi",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp, top = 5.dp, end = 16.dp, bottom = 0.dp)
            )

            // Search bar
            Serchbar(
                text = homeViewModel.serchtext,
                setstring = { setstr -> homeViewModel.serserchtext(setstr) },
                setfoucs = { lund -> homeViewModel.updateisFocuse(lund) },
                getfocus = homeViewModel.isFocused
            ) {
                homeViewModel.setfilter(true)
            }

            // Filter dialog
            AnimatedVisibility(visible = homeViewModel.isFocused) {
                if (homeViewModel.filter) {
                    FilterDialog(
                        Modifier,
                        true,
                        setDialogstate = { it -> homeViewModel.setfilter(it) },
                        setlocationvalue = { loat ->
                            filterLocation = when (loat) {
                                0 -> Locatation.city
                                1 -> Locatation.state
                                2 -> Locatation.choosenlocatation
                                else -> ""
                            }

                        },
                        setpricevalue = { price ->
                            priceRange = when (price) {
                                0 -> 50..100
                                1 -> 100..1000
                                2 -> 1000..10000
                                else -> 0..100
                            }
                            Log.d("price", priceRange.toString())
                        },
                        setDatevalue = { choseDate ->
                            chosenDate = choseDate
                        },
                        setAvailabilityvalue = { available = it },
                        setFreeDelivery = { isFreeDelivery = it },
                        homeMVVM = homeViewModel
                    )
                }
            }

            // Display filtered items
            AnimatedVisibility(
                visible = homeViewModel.isFocused
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(filteredItems) { item ->
                            IteamSearch(ShowData = item)
                        }
                    }
                }
            }

            // Image carousel
            ImageCarouselCard(images)
        }

        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // 4 items per row
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = category.second,
                        contentDescription = category.first,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category.first,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }
            }
        }

        Text(
            text = "Deals of the Day",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Two columns
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(7) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(
                                    this.constraints.maxHeight
                                        .div(2).dp
                                )
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.White),
                        )
                        AsyncImage(
                            model = images[0],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.medium),
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        /** Product's interactions */
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "$12",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                ),
                            )

                        }

                        Text(
                            modifier = Modifier,
                            text = "Title",
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 2,
                        )
                    }

                }
            }
        }
    }
}

fun filterItems(
    items: List<Search_dataList>,
    priceRange: IntRange,
    locationFilter: String,
    available: Boolean,
    freeDelivery: Boolean
): List<Search_dataList> {
    return items.filter { item ->
        // Check price range
        val priceFilter = item.price in priceRange

        // Check location
        val locationFilter = locationFilter.isEmpty() || item.Locatation.contains(locationFilter, true)

        // Check availability
        val availabilityFilter = !available || item.avalable

        // Check delivery type
        val deliveryFilter = !freeDelivery || item.deliveryType == "Free"

        // Apply all filters
        priceFilter && locationFilter && availabilityFilter && deliveryFilter
    }
}
fun lundmaderchod(): MutableList<Search_dataList> {
    val listofdata = mutableListOf<Search_dataList>()
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 100,

            Locatation = "Durg"
            , deliveryType="Free"
            ,avalable = false
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 50,
            Locatation = "Bhilai"
            , deliveryType="Paid"
            ,avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 5000,
            Locatation = "Raipur"
            , deliveryType="Paid"
            ,avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 500,
            Locatation = "khoka"
            ,avalable = true
            , deliveryType="Free"
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 3200,
            Locatation = "supila"
            , deliveryType="Paid"
            ,avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 6500,
            Locatation = "chhatishgur"
            ,avalable = false
            , deliveryType="Free"
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 300,
            Locatation = "india"
            , deliveryType="Paid"
            ,avalable = false
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 6600,
            Locatation = "usa"
            , deliveryType="Free"
            ,avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 2000,
            Locatation = "uk",
            deliveryType="Free"
            ,avalable = false
        )
    )
    return listofdata
}

fun filterItems(
    items: List<Search_dataList>,
    lowPrice: Boolean,
    midPrice: Boolean,
    highPrice: Boolean,
    nearMe: Boolean,
    sameState: Boolean,
    available: Boolean,
    unavailable: Boolean,
    freeDelivery: Boolean,
    paid: Boolean
): List<Search_dataList> {
    return items.filter { item ->
        // Check price range
        val priceFilter = when {
            lowPrice -> item.price in 0..100
            midPrice -> item.price in 101..500
            highPrice -> item.price > 500
            else -> true
        }

        // Check location
        val locationFilter = when {
            nearMe -> item.Locatation == "your_nearby_location" // Replace with actual nearby location
            sameState -> item.Locatation == "your_state_location" // Replace with actual state location
            else -> true
        }

        // Check availability
        val availabilityFilter = when {
            available -> item.avalable
            unavailable -> !item.avalable
            else -> true
        }

        // Check delivery type
        val deliveryFilter = when {
            freeDelivery -> item.deliveryType == "Free"
            paid -> item.deliveryType == "Paid"
            else -> true
        }

        // Apply all filters
        priceFilter && locationFilter && availabilityFilter && deliveryFilter
    }
}