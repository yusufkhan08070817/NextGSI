package com.ionexa.nextgsi

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ionexa.nextgsi.Classes.LocationProvider
import com.ionexa.nextgsi.Components.LocationScreen

import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.Loginmvvm
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.Pages.HomePage
import com.ionexa.nextgsi.Pages.LoginPage
import com.ionexa.nextgsi.Pages.ProductDetailScreen

import com.ionexa.nextgsi.Pages.ProfilePage

import com.ionexa.nextgsi.Pages.Splashscreen
import com.ionexa.nextgsi.SingleTon.NaveLabels
import com.ionexa.nextgsi.ui.theme.NextGsiTheme

class MainActivity : ComponentActivity() {
    private val LoginViewModel by viewModels<Loginmvvm>()
    private val HomeViewModel by viewModels<HomeMVVM>()
    private val ProfileViewModel by viewModels<Loginmvvm>()
    private val MapeViewModel by viewModels<MapeKCMVVM>()

    private val locationProvider by lazy { LocationProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Main(
                LoginViewModel = LoginViewModel,
                HomeViewModel = HomeViewModel,
                ProfileViewModel = ProfileViewModel
            )


            // A surface container using the 'background' color from the theme

        }
    }


}
// mapestuf
// LocationScreen(locationProvider,MapeViewModel)
/*
   val latitude by rememberUpdatedState(Locatation.latitude)
            val longitude by rememberUpdatedState(Locatation.longitude)

            // Update the displayed text when location changes
            LaunchedEffect(latitude, longitude) {
                // This will recompose when latitude or longitude changes
                Toast.makeText(this@MainActivity, "Latitude: $latitude, Longitude: $longitude" , Toast.LENGTH_SHORT).show()
            }

            // Display the latitude and longitude

         Column {

             MapeComp(
                 Modifier
                     .fillMaxWidth(1f)
                     .fillMaxHeight(1f),MapeViewModel,this@MainActivity)
         }
            Button(onClick = {  MapeViewModel.updateLocataionString("Lund");
                MapeViewModel.searchLocation() }, modifier = Modifier.offset(100.dp,100.dp)) {

            }
           Text(text = "Latitude: $latitude, Longitude: $longitude", modifier = Modifier.offset(10.dp,200.dp))
         }
 */

@Composable
fun Main(
    LoginViewModel: Loginmvvm,
    HomeViewModel: HomeMVVM,
    ProfileViewModel: Loginmvvm
) {
    val navcontroller = rememberNavController()
    NavHost(navController = navcontroller, startDestination = "SplashScreen") {
        composable(NaveLabels.Login) {
            NextGsiTheme {
                LoginPage(LoginViewModel, navcontroller)
            }
        }
        composable("SplashScreen") {
            Splashscreen(modifier = Modifier, navcontroller)
        }
        composable(NaveLabels.Home) {
            HomePage(modifier = Modifier, navcontroller, HomeViewModel)
        }
        composable(NaveLabels.Profile) {
            ProfilePage(modifier = Modifier, navcontroller)
        }
        composable(NaveLabels.product){
            ProductDetailScreen()
        }
    }
}

