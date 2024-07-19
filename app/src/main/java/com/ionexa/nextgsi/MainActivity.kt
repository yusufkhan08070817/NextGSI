package com.ionexa.nextgsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.Pages.HomePage
import com.ionexa.nextgsi.Pages.LoginPage

import com.ionexa.nextgsi.Pages.Navagatation

import com.ionexa.nextgsi.Pages.Splashscreen
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val HomeViewModel by viewModels<HomeMVVM>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navcontroller = rememberNavController()
            NavHost(navController = navcontroller, startDestination = "SplashScreen") {
                composable("LoginPage") {
                    NextGsiTheme {
                        LoginPage(LoginViewModel,navcontroller)
                    }
                }
                composable("SplashScreen") {
                    Splashscreen(modifier = Modifier, navcontroller)
                }
                composable("Home") {
                    HomePage(modifier = Modifier, navcontroller,HomeViewModel)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private  fun pervo () {
    Canvas(modifier = Modifier.size(300.dp, 200.dp)) {
        val width = size.width
        val height = size.height
        val radius = height / 2

        val path = androidx.compose.ui.graphics.Path().apply {
            // Start at the top-left corner
            moveTo(0f, 0f)
            // Line to the top-right corner
            lineTo(width, 0f)
            // Line to the bottom-right corner
            lineTo(width, height)
            // Line to the bottom-left corner
            lineTo(0f, height)
            // Line back to the top-left corner, creating the rectangle
            lineTo(0f, 0f)
            // Move to the center of the top edge for the semicircular cutout
            moveTo(width / 2 - radius, 0f)
            // Draw the semicircular cutout
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left = width / 2 - radius,
                    top = -radius,
                    right = width / 2 + radius,
                    bottom = radius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }

        drawPath(path = path, color = Color(0xFFFFA500), style = Fill)
    }
}


