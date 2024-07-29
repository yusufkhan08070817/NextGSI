package com.ionexa.nextgsi.MVVM

import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MapeKCMVVM : ViewModel() {
    var webView: WebView? by mutableStateOf(null)
        private set

    var locataionString by mutableStateOf("")
        private set

    var Latitude by mutableStateOf(0.0)
        private set

    var Longitude by mutableStateOf(0.0)
        private set

    var currentLatitude by mutableStateOf(0.0)
        private set
    var currentLongitude by mutableStateOf(0.0)
        private set
    var PickedLatitude by mutableStateOf(0.0)
        private set
    var PickedLongitude by mutableStateOf(0.0)
        private set

    fun updateCurrentLatitude(newCurrentLatitude: Double) {
        currentLatitude = newCurrentLatitude

    }

    fun updateCurrentLongitude(newCurrentLongitude: Double) {
        currentLongitude = newCurrentLongitude
    }

    fun updatePickedLatitude(newPickedLatitude: Double) {
        PickedLatitude = newPickedLatitude
    }

    fun updatePickedLongitude(newPickedLongitude: Double) {
        PickedLongitude = newPickedLongitude
    }


    fun updateLatitude(newLatitude: Double) {
        Latitude = newLatitude
    }

    fun updateLongitude(newLongitude: Double) {
        Longitude = newLongitude
    }

    fun updateWebView(newWebView: WebView) {
        webView = newWebView
    }

    fun updateLocataionString(newLocataionString: String) {
        locataionString = newLocataionString
    }

    fun searchLocation() {
        webView?.evaluateJavascript("searchLocation('$locataionString');", null)
    }

    fun searchByCordinates(latitude: Double, longitude: Double) {
        webView?.evaluateJavascript("setLocation($latitude, $longitude);", null)

    }

    var yourlatitude by mutableStateOf(0.0)
        private set
    var yourlongitude by mutableStateOf(0.0)
        private set
    fun updateYourLocatation(latitude: Double, longitude: Double) {
        yourlatitude = latitude
        yourlongitude = longitude

    }
    var yourcurrentLocatationString by mutableStateOf("")
        private set
    fun updateyourcurrentLocatationString(value:String){
        yourcurrentLocatationString=value
    }

}
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