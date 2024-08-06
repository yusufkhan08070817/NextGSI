package com.ionexa.nextgsi.Components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.ionexa.nextgsi.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*

import com.ionexa.nextgsi.DataClass.Productdata
import com.ionexa.nextgsi.DataClass.Search_dataList
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.SingleTon.Locatation
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.SingleTon.Navigation
import com.ionexa.nextgsi.ui.theme.Black
import com.ionexa.nextgsi.ui.theme.Indigo
import com.ionexa.nextgsi.ui.theme.LightlightText
import com.ionexa.nextgsi.ui.theme.SlateBlue
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Serchbar(
    modifier: Modifier = Modifier,
    text: String,
    setstring: (String) -> Unit,
    setfoucs: (Boolean) -> Unit,
    getfocus: Boolean,
    filter: () -> Unit
) {


    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .height(58.dp)
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            modifier = Modifier
                .padding(14.dp, 12.dp, 14.dp, 0.dp)
                .fillMaxWidth(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth(
                    1f
                )
            ) {
                TextField(
                    value = text,
                    onValueChange = { setstring(it) },
                    modifier = modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            setfoucs(focusState.isFocused)
                        },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (getfocus) {
                                filter()
                            }
                        }, modifier = Modifier.padding(5.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.filter),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "filter",
                                contentScale = ContentScale.Inside,
                                colorFilter = ColorFilter.tint(if (!getfocus) LightlightText else Black)
                            )
                        }
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterDialog(
    modifier: Modifier = Modifier,

    Dialogstate: Boolean,
    setDialogstate: (Boolean) -> Unit,
    setlocationvalue: (Int) -> Unit,
    setpricevalue: (Int) -> Unit,
    setDatevalue: (String) -> Unit,
    setAvailabilityvalue: (Boolean) -> Unit,
    setFreeDelivery: (Boolean) -> Unit,
    homeMVVM: HomeMVVM
) {

var locatationset by remember {
    mutableStateOf("")
}
    Dialog(onDismissRequest = { }, properties = DialogProperties(usePlatformDefaultWidth = Dialogstate)) {
        Card(
            modifier = Modifier.padding(10.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,

                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.8f)
                    .padding(10.dp)
            ) {
                Text(text = "Filter")
                Spacer(modifier = modifier.height(20.dp))
                Text(text = "Location : ${locatationset}")
                Card(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(1f),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {}
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Check(homeMVVM.nearMe, "near me") {
                        homeMVVM.updateNearMe(it)
                        setlocationvalue(0)
                        locatationset=Locatation.city
                    }
                    Check(homeMVVM.sameState, "Same state") {
                        homeMVVM.updateSameState(it)
                        setlocationvalue(1)
                            Locatation.state
                    }
                    IconButton(onClick = { Navigation.navController.navigate(NaveLabels.SerchWithLocatation);setlocationvalue(2) }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "locatation",
                            tint = Indigo
                        )
                    }

                }
                Spacer(modifier = modifier.height(10.dp))
                Text(text = "Price")
                Card(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(1f),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {}
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Check(homeMVVM.lowPrice, "50-1k") {
                        homeMVVM.updateLowPrice(it)
                        setpricevalue(0);Log.e("price","$50")}
                    Check(homeMVVM.midPrice, "1k-10k") {
                        homeMVVM.updateMidPrice(it)
                        setpricevalue(1);Log.e("price","$10k")}
                    Check(homeMVVM.highPrice, "10k-100k") {
                        homeMVVM.updateHighPrice(it)
                        setpricevalue(2);Log.e("price","$100k")}

                }
                Spacer(modifier = modifier.height(10.dp))
                Text(text = "Date")
                MyDatePicker(updatechossingDate = {it->
                    setDatevalue(it.toString())

                })
                Text(text = "Availability")
                Card(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(1f),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {}
                Spacer(modifier = modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Check(homeMVVM.available, "Acailable") {
                        homeMVVM.updateAvailable(it)
                        setAvailabilityvalue(true)}
                    Check(homeMVVM.unavailable, "Comming soon") {
                        homeMVVM.updateUnavailable(it)
                        setAvailabilityvalue(false)}


                }
                Spacer(modifier = modifier.height(10.dp))
                Text(text = "Delivery")
                Card(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(1f),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {}
                Spacer(modifier = modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Check(homeMVVM.freeDelivery, "free Delivery") {
                        homeMVVM.updateFreeDelivery(it)
                        setFreeDelivery(true)}
                    Check(homeMVVM.paid, "Payed Delivery") {
                        homeMVVM.updatePaid(it)
                        setFreeDelivery(false)}



                }
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                ) {
                    Button(
                        onClick = { setDialogstate(false) }, colors = ButtonDefaults.buttonColors(
                            Indigo
                        )
                    ) {

                        Text(text = "cancel")
                    }
                    Button(
                        onClick = { setDialogstate(false) }, colors = ButtonDefaults.buttonColors(
                            Indigo
                        )
                    ) {

                        Text(text = "submit")
                    }
                }

            }
        }

    }

}

@Composable
fun Check(value: Boolean, text: String, setvalue: (Boolean) -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = value,
            onCheckedChange = { setvalue(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Indigo,  // Customize the color when checked
                uncheckedColor = SlateBlue,       // Customize the color when unchecked
                checkmarkColor = Color.White       // Customize the checkmark color
            )
        )
        Text(text = text)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(updatechossingDate:(LocalDate)->Unit) {
    val currentDate = LocalDate.now()
    val currentDateMillis = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val dateState = rememberDatePickerState(initialSelectedDateMillis = currentDateMillis)
    var chosenDate by remember { mutableStateOf(currentDate) }

    var clicked by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { clicked = !clicked }) {
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "date", tint = Indigo)
    }
    if (clicked)
        Dialog(onDismissRequest = { clicked }) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(Modifier.fillMaxWidth(1f)) {
                    DatePicker(
                        state = dateState
                    )
                    Row(
                        Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = { clicked = false }) {
                            Text(text = "Cancle")
                        }
                        TextButton(onClick = { clicked = false }) {
                            Text(text = "Submit")
                            updatechossingDate(chosenDate)
                        }
                    }
                }
            }

        }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.Black,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000,
    pagerState: com.google.accompanist.pager.PagerState = rememberPagerState(),
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage, isDragged) {
        if (!isDragged) {
            delay(autoSlideDuration)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = pagerState.currentPage,
                dotSize = 8.dp
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageCarouselCard(images: List<String>) {
    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
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
                        .height(200.dp)
                )
            }
        )
    }
}

@Composable
fun ProductList(modifier: Modifier = Modifier, List: List<Productdata>) {


}

@Composable
fun DrawableButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.LightGray,
    iconTint: Color = Color.Unspecified,
    onButtonClicked: () -> Unit,
    painter: Painter,
    shape: Shape = MaterialTheme.shapes.medium,
    iconSize: Dp = 36.dp,
    elevation: Dp = 0.dp,
    paddingValue: PaddingValues = PaddingValues(4.dp),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                onClick = {
                    if (enabled) onButtonClicked()
                }
            )
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(iconSize),
            painter = painter,
            contentDescription = "icon next",
            tint = iconTint,
        )
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    Card {
        Column {

        }
    }

}

@Composable
fun IteamSearch(ShowData: Search_dataList) {
    Card (modifier = Modifier
        .fillMaxWidth(1f)
        .height(80.dp)
        .padding(10.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.elevatedCardElevation(5.dp)){
        Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize(1f)) {
            AsyncImage(
                model = ShowData.image,
                contentDescription = "Product IMage",
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
                    .padding(start = 20.dp)
                    .shadow(shape = CircleShape, elevation = 5.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "${ShowData.name} and price ${ShowData.price} loc ${ShowData.Locatation} ",Modifier.padding(start = 50.dp))
        }
    }
}

