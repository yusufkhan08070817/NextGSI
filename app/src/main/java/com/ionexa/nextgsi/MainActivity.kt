package com.ionexa.nextgsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.Pages.HomePage
import com.ionexa.nextgsi.Pages.LoginPage

import com.ionexa.nextgsi.Pages.Navagatation
import com.ionexa.nextgsi.Pages.ProfilePage

import com.ionexa.nextgsi.Pages.Splashscreen
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val HomeViewModel by viewModels<HomeMVVM>()
private val ProfileViewModel by viewModels<Loginmvvm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
         Main(LoginViewModel = LoginViewModel, HomeViewModel =HomeViewModel , ProfileViewModel = ProfileViewModel)
        }
    }


}

@Composable
fun Main(modifier: Modifier = Modifier, LoginViewModel: Loginmvvm,HomeViewModel: HomeMVVM, ProfileViewModel: Loginmvvm) {
    val navcontroller = rememberNavController()
    NavHost(navController = navcontroller, startDestination = "SplashScreen") {
        composable(NaveLabels.Login) {
            NextGsiTheme {
                LoginPage(LoginViewModel,navcontroller)
            }
        }
        composable("SplashScreen") {
            Splashscreen(modifier = Modifier, navcontroller)
        }
        composable(NaveLabels.Home) {
            HomePage(modifier = Modifier, navcontroller,HomeViewModel)
        }
        composable(NaveLabels.Profile) {
            ProfilePage(modifier = Modifier,navcontroller)
        }

    }
}

@Preview
@Composable
private fun prev() {
    
}



