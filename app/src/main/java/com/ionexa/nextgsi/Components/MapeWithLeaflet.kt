package com.ionexa.nextgsi.Components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ionexa.nextgsi.MVVM.HomeMVVM
import com.ionexa.nextgsi.MVVM.MapeKCMVVM
import com.ionexa.nextgsi.MainActivity
import com.ionexa.nextgsi.SingleTon.Locatation


@Composable
fun MapeComp(
    modifier: Modifier = Modifier,
    MapViewModel: MapeKCMVVM,
    context: Context,

) {
    val MapPermision= Manifest.permission.ACCESS_FINE_LOCATION
var isMapPermissionGranted by remember { mutableStateOf(checkMapPermision(MapPermision, context = context)) }
    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {isgranted->
        if (isgranted) {
            Log.d("LeafletMapView", "Map permission granted")
            isMapPermissionGranted = true
        }
    }
    LaunchedEffect(true) {
        launcher.launch(MapPermision)
    }
    MapeKC(modifier,
        onLocationPicked = { lat, lng ->
            Log.d("LeafletMapView", "Location picked: $lat, $lng")

            MapViewModel.updatePickedLatitude(lat)
            MapViewModel.updatePickedLongitude(lng)
        },
        onCurrentLocation = { lat, lng ->
            Log.d("LeafletMapView", "Current location: $lat, $lng")

            MapViewModel.updateCurrentLatitude(lat)
            MapViewModel.updateCurrentLongitude(lng)

        }, onMapeClicked = {
            MapViewModel.webView?.evaluateJavascript("getLocation();", null)
        }


    ) { webViewInstance ->
        MapViewModel.updateWebView(webViewInstance)
    }


}
private fun checkMapPermision(permission: String,context: Context):Boolean{
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

/*
 */
