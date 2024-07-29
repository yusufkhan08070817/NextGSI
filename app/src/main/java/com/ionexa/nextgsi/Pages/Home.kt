package com.ionexa.nextgsi.Pages

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.material.datepicker.MaterialDatePicker
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.Components.FilterDialog
import com.ionexa.nextgsi.Components.ImageCarouselCard
import com.ionexa.nextgsi.Components.IteamSearch
import com.ionexa.nextgsi.Components.LocationScreen
import com.ionexa.nextgsi.Components.Serchbar
import com.ionexa.nextgsi.DataClass.Search_dataList
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.R
import com.ionexa.nextgsi.SingleTon.Locatation
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.ui.theme.Mediumpurple
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
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
    )

    // Filter items based on the selected filters
    val filteredItems = filterItems(
        items = lundmaderchod(),
        priceRange = priceRange,
        locationFilter = filterLocation,
        available = available,
        freeDelivery = isFreeDelivery
    )


    Column(modifier.fillMaxSize()) {
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
                    .fillMaxHeight()
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