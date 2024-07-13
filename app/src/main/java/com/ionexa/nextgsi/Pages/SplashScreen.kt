package com.ionexa.nextgsi.Pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.ionexa.nextgsi.R
import kotlinx.coroutines.delay

@Composable
fun Splashscreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoAppearAnimation(navController)
    }
}

@Composable
fun LogoAppearAnimation(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    var isPlaying by remember { mutableStateOf(true) }
    var animationCompleted by remember { mutableStateOf(false) }

    // Listen to animation state
    LaunchedEffect(composition) {
        composition?.let {
            val durationMillis = it.duration
            if (durationMillis != null) {
                delay(durationMillis.toLong())
                isPlaying = false
                animationCompleted = true
            }
        }
    }

    if (animationCompleted) {
        navController.navigate("LoginPage")
    }

    LottieAnimation(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1
    )
}
