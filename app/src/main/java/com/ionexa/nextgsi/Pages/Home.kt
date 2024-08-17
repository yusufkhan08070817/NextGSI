package com.ionexa.nextgsi.Pages

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.google.gson.JsonObject
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.Components.FilterDialog
import com.ionexa.nextgsi.Components.ImageCarouselCard
import com.ionexa.nextgsi.Components.IteamSearch
import com.ionexa.nextgsi.Components.LocationScreen
import com.ionexa.nextgsi.Components.Serchbar
import com.ionexa.nextgsi.DataClass.ExtraInfo
import com.ionexa.nextgsi.DataClass.ProductTypeId
import com.ionexa.nextgsi.DataClass.Review
import com.ionexa.nextgsi.DataClass.Search_dataList
import com.ionexa.nextgsi.FBFireBase.FSDB
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.MVVM.ProductpageMvvm
import com.ionexa.nextgsi.SingleTon.Locatation
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.common
import com.ionexa.nextgsi.SingleTon.getLocationName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeMVVM,
    locationProvider: LocationProvider,
    ProductpageMvvm: ProductpageMvvm,
    mapViewModel: MapeKCMVVM,
) {
    // Define filter states
    var priceRange by remember { mutableStateOf(0..100) }
    var chosenDate by remember { mutableStateOf("") }
    var available by remember { mutableStateOf(false) }
    var isFreeDelivery by remember { mutableStateOf(false) }
    var filterLocation by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val fsds = FSDB()
    var currentlocatation by remember { mutableStateOf("") }
    var serchSchima = remember { mutableStateListOf<ProductTypeId>() }
LaunchedEffect(key1 = Locatation.gpslocatation ) {
    currentlocatation=Locatation.gpslocatation
}

    // Fetch and update data
    LaunchedEffect(homeViewModel.serchtext, homeViewModel.isFocused) {

        fsds.getDataFromFireStoreDB("Product", homeViewModel.serchtext.trim(), onSuccess = { data ->
            Log.e("FSDB", data.toString())

            if (data != null) {
                serchSchima.clear() // Clear the list to avoid duplicates

                data.forEach { (_, productMap) ->
                    val map = productMap as? Map<String, Any> ?: return@forEach

                    val product = ProductTypeId(
                        productid = map["productid"] as? String ?: "",
                        name = map["name"] as? String ?: "",
                        price = map["price"] as? String ?: "",
                        images = (map["images"] as? List<*> ?: listOf<String>()).filterIsInstance<String>(),
                        offer = map["offer"] as? String ?: "",
                        seller = map["seller"] as? String ?: "",
                        rating = map["ratting"] as? String ?: "",
                        address = map["address"] as? String ?: "",
                        productQuantity = map["productQuantity"] as? String ?: "",
                        types = map["types"] as? String ?: "",
                        productDetails = map["productDetails"] as? String ?: "",
                        shopid = map["shopid"] as? String ?: "",
                        extraInfo = (map["extraInfo"] as? Map<String, Any>)?.let { extraInfoMap ->
                            fsds.hashMapToDataClass(extraInfoMap, ExtraInfo::class)
                        } ?: ExtraInfo(""),
                        review = map["review"] as? String ?: "",
                        availableStock = map["AvailableStock"] as? String ?: ""
                    )

                    Log.e("data", product.toString())
                    serchSchima.add(product)
                }
            }
        }, onFailure = {
            Log.d("error", it.toString())
        })
    }

    // Location Screen
    LocationScreen(Mapekcmvm = mapViewModel, locationProvider = locationProvider)

    val images = listOf(
        "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
    )

    // Filter items based on selected filters
    val filteredItems = filterItems(
        items = lundmaderchod(),
        priceRange = priceRange,
        locationFilter = filterLocation,
        available = available,
        freeDelivery = isFreeDelivery
    )

    MainHomeList(modifier = Modifier.fillMaxWidth(), data = createDummyData()) {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            val customPurple200 = Color(0xFFBB86FC)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                        clip = true
                    )
                    .background(
                        customPurple200,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                Text(
                    text = "Delivering to: $currentlocatation",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 45.dp, end = 16.dp)
                )
                Text(
                    text = mapViewModel.locataionString,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 5.dp, end = 16.dp)
                )

                // Search bar
                Serchbar(
                    text = homeViewModel.serchtext,
                    setstring = homeViewModel::serserchtext,
                    removedata = serchSchima::clear,
                    setfoucs = { Focus ->
                        homeViewModel.updateisFocuse(Focus)
                        if (Focus) serchSchima.clear()
                    },
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
                AnimatedVisibility(visible = homeViewModel.isFocused) {
                    Card(
                        modifier = Modifier
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
                            if (serchSchima.isNotEmpty()) {
                                items(serchSchima.size) { item ->
                                    IteamSearch(ShowData = serchSchima[item]){data->
                                        ProductpageMvvm.updateproduct(data)
                                        ProductpageMvvm.updateindex(item)
                                        navController.navigate(NaveLabels.product)
                                    }
                                }
                            }
                        }
                    }
                }

                // Image carousel
                ImageCarouselCard(images)
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
        val locationFilter =
            locationFilter.isEmpty() || item.Locatation.contains(locationFilter, true)

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

            Locatation = "Durg",
            deliveryType = "Free",
            avalable = false
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 50,
            Locatation = "Bhilai",
            deliveryType = "Paid",
            avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 5000,
            Locatation = "Raipur",
            deliveryType = "Paid",
            avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 500,
            Locatation = "khoka",
            avalable = true,
            deliveryType = "Free"
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 3200,
            Locatation = "supila",
            deliveryType = "Paid",
            avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 6500,
            Locatation = "chhatishgur",
            avalable = false,
            deliveryType = "Free"
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 300,
            Locatation = "india",
            deliveryType = "Paid",
            avalable = false
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 6600,
            Locatation = "usa",
            deliveryType = "Free",
            avalable = true
        )
    )
    listofdata.add(
        Search_dataList(
            image = "https://www.allrecipes.com/thmb/wROq_ShvsPWFEGyYEsqj9ahoa08=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/19863best-burger-everFranceC4x3-c9c7d5cae40b4a58a110a33e04b531d1.jpg",
            name = "burger",
            productid = 34,
            price = 2000,
            Locatation = "uk",
            deliveryType = "Free",
            avalable = false
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

fun createDummyData(): MutableList<MutableList<tempdata>> {
    return mutableListOf(
        mutableListOf(
            tempdata(
                "https://media.istockphoto.com/id/1457979959/photo/snack-junk-fast-food-on-table-in-restaurant-soup-sauce-ornament-grill-hamburger-french-fries.jpg?s=2048x2048&w=is&k=20&c=_AdAtGBXgtZjfJQVbDYS6Eku8m3h05p2E2p0V1uKlUo=",
                "Title 1"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428412216/photo/a-male-chef-pouring-sauce-on-meal.jpg?s=2048x2048&w=is&k=20&c=4kootjYOsFPf6Z-1sxc0d5_kAq6Xu5zJiu-PsVNKkDE=",
                "Title 2"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1670740967011-86730910a2e5?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1312283557/photo/classic-thai-food-dishes.jpg?s=2048x2048&w=is&k=20&c=DUmzzaJlEb8lfQQdL85DKJ98CUQnuWABfJq2SWKh9Fk=",
                "Title 4"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428409514/photo/a-male-chef-serving-a-fine-dining-dish-in-a-restaurant.jpg?s=2048x2048&w=is&k=20&c=yWRYRZG38WbZwPwajj9Vl2YKNiRhzIMarmYoFDMronE=",
                "Title 5"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1053855542/photo/chopstick-with-nigiri-sushi-piece.jpg?s=2048x2048&w=is&k=20&c=zgXH_6bV-Zto8zh6oGH7xNvpkmZTCenhWktQMzcR4fM=",
                "Title 6"
            ),
            tempdata(
                "https://media.istockphoto.com/id/502840530/photo/luxury-restaurant-table-on-sunset.jpg?s=2048x2048&w=is&k=20&c=kXOW1uVoz8nwYsW2bhdn-q8kgwlM2_12s4Zm1UZWpJs=",
                "Title 7"
            ),


            ),
        mutableListOf(
            tempdata(
                "https://plus.unsplash.com/premium_photo-1681711647066-ef84575c0d95?q=80&w=2000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 1"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1560343090-f0409e92791a?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 2"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1554866585-cd94860890b7?q=80&w=1965&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 4"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1674688194029-17dda3aaf779?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 5"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=1996&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 6"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1563170351-be82bc888aa4?q=80&w=1936&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 7"
            ),
        ),
        mutableListOf(
            tempdata(
                "https://media.istockphoto.com/id/1457979959/photo/snack-junk-fast-food-on-table-in-restaurant-soup-sauce-ornament-grill-hamburger-french-fries.jpg?s=2048x2048&w=is&k=20&c=_AdAtGBXgtZjfJQVbDYS6Eku8m3h05p2E2p0V1uKlUo=",
                "Title 1"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428412216/photo/a-male-chef-pouring-sauce-on-meal.jpg?s=2048x2048&w=is&k=20&c=4kootjYOsFPf6Z-1sxc0d5_kAq6Xu5zJiu-PsVNKkDE=",
                "Title 2"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1670740967011-86730910a2e5?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1312283557/photo/classic-thai-food-dishes.jpg?s=2048x2048&w=is&k=20&c=DUmzzaJlEb8lfQQdL85DKJ98CUQnuWABfJq2SWKh9Fk=",
                "Title 4"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428409514/photo/a-male-chef-serving-a-fine-dining-dish-in-a-restaurant.jpg?s=2048x2048&w=is&k=20&c=yWRYRZG38WbZwPwajj9Vl2YKNiRhzIMarmYoFDMronE=",
                "Title 5"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1053855542/photo/chopstick-with-nigiri-sushi-piece.jpg?s=2048x2048&w=is&k=20&c=zgXH_6bV-Zto8zh6oGH7xNvpkmZTCenhWktQMzcR4fM=",
                "Title 6"
            ),
            tempdata(
                "https://media.istockphoto.com/id/502840530/photo/luxury-restaurant-table-on-sunset.jpg?s=2048x2048&w=is&k=20&c=kXOW1uVoz8nwYsW2bhdn-q8kgwlM2_12s4Zm1UZWpJs=",
                "Title 7"
            ),


            ),
        mutableListOf(
            tempdata(
                "https://plus.unsplash.com/premium_photo-1681711647066-ef84575c0d95?q=80&w=2000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 1"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1560343090-f0409e92791a?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 2"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1554866585-cd94860890b7?q=80&w=1965&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 4"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1674688194029-17dda3aaf779?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 5"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=1996&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 6"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1563170351-be82bc888aa4?q=80&w=1936&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 7"
            ),
        ),
        mutableListOf(
            tempdata(
                "https://media.istockphoto.com/id/1457979959/photo/snack-junk-fast-food-on-table-in-restaurant-soup-sauce-ornament-grill-hamburger-french-fries.jpg?s=2048x2048&w=is&k=20&c=_AdAtGBXgtZjfJQVbDYS6Eku8m3h05p2E2p0V1uKlUo=",
                "Title 1"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428412216/photo/a-male-chef-pouring-sauce-on-meal.jpg?s=2048x2048&w=is&k=20&c=4kootjYOsFPf6Z-1sxc0d5_kAq6Xu5zJiu-PsVNKkDE=",
                "Title 2"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1670740967011-86730910a2e5?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1312283557/photo/classic-thai-food-dishes.jpg?s=2048x2048&w=is&k=20&c=DUmzzaJlEb8lfQQdL85DKJ98CUQnuWABfJq2SWKh9Fk=",
                "Title 4"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1428409514/photo/a-male-chef-serving-a-fine-dining-dish-in-a-restaurant.jpg?s=2048x2048&w=is&k=20&c=yWRYRZG38WbZwPwajj9Vl2YKNiRhzIMarmYoFDMronE=",
                "Title 5"
            ),
            tempdata(
                "https://media.istockphoto.com/id/1053855542/photo/chopstick-with-nigiri-sushi-piece.jpg?s=2048x2048&w=is&k=20&c=zgXH_6bV-Zto8zh6oGH7xNvpkmZTCenhWktQMzcR4fM=",
                "Title 6"
            ),
            tempdata(
                "https://media.istockphoto.com/id/502840530/photo/luxury-restaurant-table-on-sunset.jpg?s=2048x2048&w=is&k=20&c=kXOW1uVoz8nwYsW2bhdn-q8kgwlM2_12s4Zm1UZWpJs=",
                "Title 7"
            ),


            ),
        mutableListOf(
            tempdata(
                "https://plus.unsplash.com/premium_photo-1681711647066-ef84575c0d95?q=80&w=2000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 1"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1560343090-f0409e92791a?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 2"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1554866585-cd94860890b7?q=80&w=1965&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 3"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 4"
            ),
            tempdata(
                "https://plus.unsplash.com/premium_photo-1674688194029-17dda3aaf779?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 5"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=1996&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 6"
            ),
            tempdata(
                "https://images.unsplash.com/photo-1563170351-be82bc888aa4?q=80&w=1936&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Title 7"
            ),
        ),

        )
}

/*
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
 */