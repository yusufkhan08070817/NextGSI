package com.ionexa.nextgsi.Classes

import android.util.Log
import android.webkit.JavascriptInterface
import com.ionexa.nextgsi.SingleTon.Locatation

class JSInterface(
    val onLocationPicked: (Double, Double) -> Unit,
    val onCurrentLocation: (Double, Double) -> Unit
) {
    @JavascriptInterface
    fun onLocationPicked(lat: Double, lng: Double) {
        Log.d("JSInterface", "Received location: $lat, $lng")
        Locatation.latitude=lat
        Locatation.longitude=lng
        onLocationPicked(lat, lng)
    }

    @JavascriptInterface
    fun getCurrentLocation(lat: Double, lng: Double) {
        Log.d("JSInterface", "Received current location: $lat, $lng")
        onCurrentLocation(lat, lng)
    }
}