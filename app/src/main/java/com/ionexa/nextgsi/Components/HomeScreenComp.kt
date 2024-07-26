package com.ionexa.nextgsi.Components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

import com.google.accompanist.pager.*
import com.ionexa.nextgsi.DataClass.Productdata


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Serchbar(modifier: Modifier = Modifier,text:String,setstring:(String)->Unit, filter: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth(
                    1f
                )
            ) {
                TextField(value = text,
                    onValueChange = {setstring(it)},
                    modifier = modifier.fillMaxWidth(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,

                        ),
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "serch")
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { filter() }) {
                            Image(
                                painter = painterResource(id = R.drawable.filter),
                                modifier = Modifier.padding(5.dp),
                                contentDescription = "filter",
                                contentScale = ContentScale.Inside
                            )
                        }
                    })
            }
        }
    }
}

@Composable
fun FilterDialog(
    modifier: Modifier = Modifier,

    Dialogstate: Boolean,
    setDialogstate: (Boolean) -> Unit,
    setlocationvalue: (String) -> Unit,
    setpricevalue: (String) -> Unit,
    setDatevalue: (String) -> Unit,
    setAvailabilityvalue: (String) -> Unit
) {
    var nearme by remember { mutableStateOf(false) }
    var SameState by remember { mutableStateOf(false) }
    var anyState by remember { mutableStateOf(false) }

    var price100_1k by remember { mutableStateOf(false) }
    var price1k_10k by remember { mutableStateOf(false) }
    var price10k_100k by remember { mutableStateOf(false) }


    var available by remember { mutableStateOf(false) }
    var unavailable by remember { mutableStateOf(false) }
    var upcoming by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
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
                Text(text = "Location")
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
                    Check(true, "near me") {}
                    Check(true, "Same state") {}
                    Check(true, "any state") {}

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
                    Check(true, "near me") {}
                    Check(true, "Same state") {}
                    Check(true, "any state") {}

                }
                Spacer(modifier = modifier.height(10.dp))
                Text(text = "Date")
                MyDatePicker()
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
                    Check(true, "near me") {}
                    Check(true, "Same state") {}
                    Check(true, "any state") {}

                }
                Spacer(modifier = modifier.height(10.dp))
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
                    Check(true, "near me") {}
                    Check(true, "Same state") {}
                    Check(true, "any state") {}

                }
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(20.dp, 50.dp, 20.dp, 10.dp)
                ) {
                    Button(onClick = { setDialogstate(false)}, colors = ButtonDefaults.buttonColors(Color(0xFF0386D0))) {

                        Text(text = "cancel")
                    }
                    Button(onClick = { setDialogstate(false)}, colors = ButtonDefaults.buttonColors(Color(0xFF0386D0))) {

                        Text(text = "submit")
                    }
                }

            }
        }

    }

}

@Composable
fun Check(value: Boolean, text: String, setvalue: (Boolean) -> Unit) {
    var va by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = va,
            onCheckedChange = { va=it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF65C6FC),  // Customize the color when checked
                uncheckedColor = Color(0xFF0386D0),       // Customize the color when unchecked
                checkmarkColor = Color.White       // Customize the checkmark color
            )
        )
        Text(text = text)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker() {
    val dateState = rememberDatePickerState()
    var clicked by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { clicked=!clicked }) {
        Icon(imageVector = Icons.Filled.DateRange, contentDescription ="date", tint = Color(0xFF0386D0) )
    }
    if (clicked)
        Dialog(onDismissRequest = { clicked }) {
            Card (colors = CardDefaults.cardColors(
                containerColor = Color.White
            )){
                Column(Modifier.fillMaxWidth(1f)) {
                    DatePicker(
                        state = dateState
                    )
                    Row (Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceAround){
                        TextButton(onClick = { clicked=false}) {
                            Text(text = "Cancle")
                        }
                        TextButton(onClick = { clicked=false}) {
                            Text(text = "Submit")
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
fun ProductList(modifier: Modifier = Modifier,List:List<Productdata>) {


}

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    Card {
        Column {

        }
    }

}