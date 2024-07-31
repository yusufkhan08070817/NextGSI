package com.ionexa.nextgsi.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ionexa.nextgsi.Components.MapeComp
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.SingleTon.Locatation
import com.ionexa.nextgsi.SingleTon.NaveLabels

@Composable
fun OrderTrackingscreen(
    modifier: Modifier = Modifier,
    imageUrl: List<String>,
    color: Color = Color.White,
    MapeViewModel: MapeKCMVVM,
    navController: NavController
) {
    val context = LocalContext.current
    val latitude by rememberUpdatedState(Locatation.latitude)
    val longitude by rememberUpdatedState(Locatation.longitude)
    Column(modifier = Modifier
        .fillMaxSize(1f)
        .padding(top = 35.dp)) {
        ItemList(
            imageUrl = listOf(

                "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "https://plus.unsplash.com/premium_photo-1683865776032-07bf70b0add1?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",

                )
        )
        MapeComp(
            Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.6f), MapeViewModel, context
        )

    }


    // Update the displayed text when location changes


    // Display the latitude and longitude


}